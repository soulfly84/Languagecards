package com.soulfly.englishcards.cards;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.soulfly.englishcards.R;
import com.soulfly.englishcards.general.LoadingDialog;
import com.soulfly.englishcards.general.LoadingView;
import com.soulfly.englishcards.model.Card;
import com.soulfly.englishcards.cards.presenters.InsertCardPresenter;
import com.soulfly.englishcards.utils.DBSchema;
import com.soulfly.englishcards.cards.views.ErrorView;
import com.soulfly.englishcards.cards.views.InsertCardView;
import com.soulfly.englishcards.utils.StringUtils;
import com.soulfly.englishcards.utils.Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsertNewCardActivity extends MvpAppCompatActivity implements InsertCardView, ErrorView {

    @InjectPresenter
    InsertCardPresenter insertCardPresenter;
    @BindView(R.id.new_card_word)
    EditText wordEditText;
    @BindView(R.id.new_card_translate)
    EditText translateEditText;
    @BindView(R.id.new_card_description)
    EditText descriptionEditText;
    @BindView(R.id.toolbar_card_theme)
    Toolbar toolbar;

    private static final String LOG_TAG = "InsertActivity";
    private String theme;
    private LoadingView loadingView;

    @OnClick(R.id.fab_new_card)
    public void onClick() {
        String word = wordEditText.getText().toString();
        String translate = translateEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        insertCardPresenter.saveCard(word, translate, description, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_card);
        ButterKnife.bind(this);

        loadingView = LoadingDialog.view(getSupportFragmentManager());
        setTextViewFilter();

        setSupportActionBar(toolbar);
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
        Toast.makeText(InsertNewCardActivity.this, R.string.error_inet,
                Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "Error inet");
    }

    @Override
    public void showSuccess(@NonNull Card card) {
        Intent intent = new Intent(InsertNewCardActivity.this, UserCardsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setTextViewFilter() {
        wordEditText.setFilters(StringUtils.setSizeForCardEditText());
        translateEditText.setFilters(StringUtils.setSizeForCardEditText());
        descriptionEditText.setFilters(StringUtils.setSizeForCardDescriptionEditText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.android_action_bar_spinner_menu, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        spinner.setPopupBackgroundResource(R.color.colorPrimary);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_thimes_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                switch (selectedItem) {
                    case (Preferences.CULTURE_ART):
                        theme = DBSchema.CardTable.Themes.THEME_CULTURE_ART;
                        break;
                    case (Preferences.MODERN_TECHNOLOGIES):
                        theme = DBSchema.CardTable.Themes.THEME_MODERN_TECHNOLOGIES;
                        break;
                    case (Preferences.SOCIETY_POLITICS):
                        theme = DBSchema.CardTable.Themes.THEME_SOCIETY_POLITICS;
                        break;
                    case (Preferences.ADVENTURE_TRAVEL):
                        theme = DBSchema.CardTable.Themes.THEME_ADVENTURE_TRAVEL;
                        break;
                    case (Preferences.NATURE_WEATHER):
                        theme = DBSchema.CardTable.Themes.THEME_NATURE_WEATHER;
                        break;
                    case (Preferences.EDUCATION_PROFESSION):
                        theme = DBSchema.CardTable.Themes.THEME_EDUCATION_PROFESSION;
                        break;
                    case (Preferences.APPEARANCE_CHARACTER):
                        theme = DBSchema.CardTable.Themes.THEME_APPEARANCE_CHARACTER;
                        break;
                    case (Preferences.CLOTHES_FASHION):
                        theme = DBSchema.CardTable.Themes.THEME_CLOTHES_FASHION;
                        break;
                    case (Preferences.SPORT):
                        theme = DBSchema.CardTable.Themes.THEME_SPORT;
                        break;
                    case (Preferences.FAMILY_RELATIONSHIP):
                        theme = DBSchema.CardTable.Themes.THEME_FAMILY_RELATIONSHIP;
                        break;
                    case (Preferences.ORDER_OF_DAY):
                        theme = DBSchema.CardTable.Themes.THEME_THE_ORDER_OF_DAY;
                        break;
                    case (Preferences.HOBBIES_FREE_TIME):
                        theme = DBSchema.CardTable.Themes.THEME_HOBBIES_FREE_TIME;
                        break;
                    case (Preferences.CUSTOMS_TRADITIONS):
                        theme = DBSchema.CardTable.Themes.THEME_CUSTOMS_TRADITIONS;
                        break;
                    case (Preferences.SHOPPING):
                        theme = DBSchema.CardTable.Themes.THEME_SHOPPING;
                        break;
                    case (Preferences.FOOD_DRINKS):
                        theme = DBSchema.CardTable.Themes.THEME_FOOD_DRINKS;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return true;
    }
}
