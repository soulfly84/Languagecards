package com.soulfly.englishcards.cards;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.soulfly.englishcards.R;
import com.soulfly.englishcards.cards.presenters.CardsPresenter;
import com.soulfly.englishcards.cards.views.CardsView;
import com.soulfly.englishcards.general.BaseAdapter;
import com.soulfly.englishcards.general.BaseView;
import com.soulfly.englishcards.general.CardsAdapter;
import com.soulfly.englishcards.general.EmptyRecyclerView;
import com.soulfly.englishcards.general.LoadingDialog;
import com.soulfly.englishcards.general.LoadingView;
import com.soulfly.englishcards.model.Card;
import com.soulfly.englishcards.utils.DBSchema;
import com.soulfly.englishcards.utils.StringUtils;
import com.soulfly.englishcards.utils.CardUtils;
import com.soulfly.englishcards.utils.PrefUtils;
import com.soulfly.englishcards.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServerCardsActivity extends MvpAppCompatActivity implements CardsView,
        BaseAdapter.OnItemClickListener<Card> {

    private static final String TAG = "ServerCardsActivity";
    @InjectPresenter
    CardsPresenter cardsPresenter;
    @BindView(R.id.recyclerView)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.empty)
    View mEmptyView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private LoadingView loadingView;
    private CardsAdapter adapter;




    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent = new Intent(ServerCardsActivity.this, InsertNewCardActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_main);
        ButterKnife.bind(this);

        loadingView = LoadingDialog.view(getSupportFragmentManager());
        initRecyclerView();
        initToolbar();
        initDrawer();

    }

    @Override
    public void onItemClick(@NonNull Card item) {
        cardsPresenter.onItemClick(item);
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
    public void showError() {
        Toast.makeText(ServerCardsActivity.this, R.string.error_inet,
                Toast.LENGTH_SHORT).show();
        adapter.clear();
    }

    @Override
    public void showCards(@NonNull List<Card> cards) {
        adapter.changeDataSet(cards);
    }

    @Override
    public void showDetails(@NonNull Card card) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyTheme_Dark_Dialog);
        String dialogMessage = StringUtils.dialogMessage(card);
        builder.setTitle(card.getWord() + " ~ " + StringUtils.returnTheme(card))
                .setMessage(card.getTranslate() + "\n\n" + dialogMessage)
                .setPositiveButton(getString(R.string.add_card), (DialogInterface dialog, int which)-> {
                        cardsPresenter.addCard(card);
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cards_main, menu);
        return true;
    }


    public void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(ServerCardsActivity.this, 3));
        recyclerView.setEmptyView(mEmptyView);
        adapter = new CardsAdapter(new ArrayList<>());
        adapter.attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(this);
    }


    public void initToolbar() {
        toolbar.setTitleTextColor(Color.parseColor(getString(R.string.color_primary)));
        setSupportActionBar(toolbar);
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.action_stock): {
                Intent intent = new Intent(ServerCardsActivity.this, ServerCardsActivity.class);
                startActivity(intent);
                return true;
            }
            case (R.id.action_my_cards): {
                Intent intent = new Intent(ServerCardsActivity.this, UserCardsActivity.class);
                startActivity(intent);
                return true;
            }

            case (R.id.action_line): {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerCardsActivity.this, 1);
                recyclerView.setLayoutManager(gridLayoutManager);
                return true;
            }
            case (R.id.action_frame): {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(ServerCardsActivity.this, 3);
                recyclerView.setLayoutManager(gridLayoutManager);
                return true;
            }
            case (R.id.action_settings): {
                String token = PrefUtils.getTokenPrefs().getString(Preferences.TOKEN, "");
                if (CardUtils.isEmptyToken(token)) {
                } else {
                    //to do...
                    //  Intent intent = new Intent(ServerCardsActivity.this, UserAuthorizedActivity.class);
                    // startActivity(intent);
                }
                return true;
            }
            case (R.id.action_about_app): {
                //to do...
                // Intent intent = new Intent(ServerCardsActivity.this, InformationActivity.class);
                //  startActivity(intent);
                return true;
            }
            case (R.id.action_sync_cards): {
                //to do...
                // Intent intent = new Intent(ServerCardsActivity.this, CardsSyncActivity.class);
                //  startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    int spinnerSelection = 0;

    public void initDrawer() {
        Log.d(TAG, "initDrawer---");

        Spinner spinner = (Spinner) findViewById(R.id.spinner_nav);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_thimes_array, R.layout.spinner_drop_title);
        adapter.setDropDownViewResource(R.layout.spinner_drop_list);

        spinner.setAdapter(adapter);
        spinner.setSelection(spinnerSelection);
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }


    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {


        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedItem = parent.getItemAtPosition(position).toString();
            Intent intent = new Intent(ServerCardsActivity.this, ServerSortedCardsActivity.class);
            Bundle b = new Bundle();
            Log.d(TAG, "itemSelectedListener position " + position);
            Log.d(TAG, "itemSelectedListener selectedItem " + selectedItem);

            switch (position) {
               case (0):
                    Intent intentAll = new Intent(ServerCardsActivity.this, ServerCardsActivity.class);
                    startActivity(intentAll);
                    break;

                case (1): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_CULTURE_ART);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (2): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_MODERN_TECHNOLOGIES);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (3): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_SOCIETY_POLITICS);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (4): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_ADVENTURE_TRAVEL);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (5): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_NATURE_WEATHER);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (6): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_EDUCATION_PROFESSION);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (7): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_APPEARANCE_CHARACTER);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (8): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_CLOTHES_FASHION);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (9): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_SPORT);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (10): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_FAMILY_RELATIONSHIP);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (11): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_THE_ORDER_OF_DAY);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (12): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_HOBBIES_FREE_TIME);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (13): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_CUSTOMS_TRADITIONS);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (14): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_SHOPPING);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
                }
                case (15): {
                    b.putString(Preferences.CARD, DBSchema.CardTable.Themes.THEME_FOOD_DRINKS);
                    intent.putExtras(b);
                    startActivity(intent);
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
