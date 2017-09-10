package com.soulfly.englishcards.cards.presenters;

import android.text.TextUtils;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.soulfly.englishcards.api.WebService;
import com.soulfly.englishcards.app.CardsApp;
import com.soulfly.englishcards.dao.DatabaseHelper;
import com.soulfly.englishcards.model.Card;
import com.soulfly.englishcards.utils.PrefUtils;
import com.soulfly.englishcards.cards.views.InsertCardView;

import java.sql.SQLException;
import java.util.UUID;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Elena on 18.03.2017.
 */
@InjectViewState
public class InsertCardPresenter extends MvpPresenter<InsertCardView> {
    private static final String LOG_TAG = "InsertCardPresenter";
    @Inject
    DatabaseHelper databaseHelper;
    @Inject
    WebService cardsService;

    public InsertCardPresenter() {
        CardsApp.getAppComponent().inject(this);
    }

    public void saveCard(String word, String translate, String description, String theme) {

        if (TextUtils.isEmpty(word)) {
            getViewState().showError();
        } else if (TextUtils.isEmpty(translate)) {
            getViewState().showError();
        } else {
            Card card = new Card(word, translate, description, theme, String.valueOf(UUID.randomUUID()), 0);
            saveCardApi(card);
            saveCardDB(card);
        }
    }

    private void saveCardApi(Card card) {

        Call<Card> callPost = cardsService.uploadCards(PrefUtils.insertToken(), card);
        callPost.enqueue(new Callback<Card>() {
            @Override
            public void onResponse(Call<Card> call, Response<Card> response) {
                Log.d(LOG_TAG, "---RESULT OK" + response.body());
            }

            @Override
            public void onFailure(Call<Card> call, Throwable t) {
                Log.d(LOG_TAG, "---RESULT Failed");
            }
        });
    }

    private void saveCardDB(Card card) {
        try {
            databaseHelper.getCardDAO().create(card);
            getViewState().showSuccess(card);
        } catch (SQLException e) {
            Log.d(LOG_TAG, e.toString());
            getViewState().showError();
        }
    }
}
