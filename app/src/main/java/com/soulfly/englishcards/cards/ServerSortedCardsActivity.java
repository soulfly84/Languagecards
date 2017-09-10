package com.soulfly.englishcards.cards;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.soulfly.englishcards.R;
import com.soulfly.englishcards.cards.presenters.ServerSortedPresenter;
import com.soulfly.englishcards.cards.views.CardsView;
import com.soulfly.englishcards.general.BaseAdapter;
import com.soulfly.englishcards.general.BaseView;
import com.soulfly.englishcards.general.CardsAdapter;
import com.soulfly.englishcards.general.EmptyRecyclerView;
import com.soulfly.englishcards.general.LoadingDialog;
import com.soulfly.englishcards.general.LoadingView;
import com.soulfly.englishcards.model.Card;
import com.soulfly.englishcards.utils.Preferences;
import com.soulfly.englishcards.utils.StringUtils;
import com.soulfly.englishcards.utils.DBSchema;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Elena on 18.03.2017.
 */

public class ServerSortedCardsActivity extends MvpAppCompatActivity implements
        BaseAdapter.OnItemClickListener<Card>, CardsView {
    @InjectPresenter
    ServerSortedPresenter presenter;
    @BindView(R.id.card_recycler_view)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty)
    View mEmptyView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private LoadingView loadingView;
    private CardsAdapter adapter;

    // private static final String LOG_TAG = "ServerListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_server);
        ButterKnife.bind(this);
        loadingView = LoadingDialog.view(getSupportFragmentManager());
        presenter.init(returnTheme());

        initRecyclerView();
        initToolbar();
        initDrawer();
    }


    @Override
    public void onItemClick(@NonNull Card item) {
        presenter.onItemClick(item);
    }

    @Override
    public void showError() {
        Toast.makeText(ServerSortedCardsActivity.this, R.string.error_inet,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCards(@NonNull List<Card> cards) {
        adapter.changeDataSet(cards);
    }

    @Override
    public void showLoading() {
        loadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        loadingView.hideLoading();
    }

    @Override
    public void showDetails(@NonNull Card card) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyTheme_Dark_Dialog);
        String dialogMessage = StringUtils.dialogMessage(card);
        builder.setTitle(card.getWord() + " ~ " + StringUtils.returnTheme(card))
                .setMessage(card.getTranslate() + "\n\n" + dialogMessage)
                .setPositiveButton(getString(R.string.add_card), (DialogInterface dialog, int which)-> {
                        presenter.addCard(card);
                })
                .show();
    }

    private String returnTheme() {
        Bundle bundle = getIntent().getExtras();
        String theme = "";
        if (bundle != null)
            theme = bundle.getString("card");
        return theme;
    }


    public void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(ServerSortedCardsActivity.this, 3));
        recyclerView.setEmptyView(mEmptyView);
        adapter = new CardsAdapter(new ArrayList<>());
        adapter.attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(this);
    }


    public void initToolbar() {
        toolbar.setTitleTextColor(Color.parseColor(getString(R.string.color_primary)));
        setSupportActionBar(toolbar);


    }



    public void initDrawer() {


        Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_thimes_array, R.layout.spinner_drop_title);
        adapter.setDropDownViewResource(R.layout.spinner_drop_list);

        spinner.setAdapter(adapter);
        //spinner.setSelection(spinnerSelection);
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {


        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case (0): {
                    Intent intentAllCards = new Intent(ServerSortedCardsActivity.this, ServerCardsActivity.class);
                    startActivity(intentAllCards);
                    break;
                }
                case (1): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_CULTURE_ART);
                    break;
                }
                case (2): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_MODERN_TECHNOLOGIES);
                    break;
                }
                case (3): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_SOCIETY_POLITICS);
                    break;
                }
                case (R.id.adventure_travel): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_ADVENTURE_TRAVEL);
                    break;
                }
                case (4): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_NATURE_WEATHER);
                    break;
                }
                case (5): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_EDUCATION_PROFESSION);
                    break;
                }
                case (6): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_APPEARANCE_CHARACTER);
                    break;
                }
                case (7): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_CLOTHES_FASHION);
                    break;
                }
                case (8): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_SPORT);
                    break;
                }
                case (9): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_FAMILY_RELATIONSHIP);
                    break;
                }
                case (10): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_THE_ORDER_OF_DAY);
                    break;
                }
                case (11): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_HOBBIES_FREE_TIME);
                    break;
                }
                case (12): {
                    adapter.clear();
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerSortedCardsActivity.this, 3);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    presenter.init(DBSchema.CardTable.Themes.THEME_CUSTOMS_TRADITIONS);
                    break;
                }


                default:
                    break;
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
