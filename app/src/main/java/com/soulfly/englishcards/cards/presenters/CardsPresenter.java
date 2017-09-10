package com.soulfly.englishcards.cards.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.soulfly.englishcards.app.CardsApp;
import com.soulfly.englishcards.dao.DatabaseHelper;
import com.soulfly.englishcards.utils.PrefUtils;
import com.soulfly.englishcards.api.WebService;
import com.soulfly.englishcards.model.Card;
import com.soulfly.englishcards.cards.views.CardsView;

import java.sql.SQLException;
import java.util.UUID;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Elena on 18.03.2017.
 */
@InjectViewState
public class CardsPresenter extends MvpPresenter<CardsView> {

    private static final String LOG_TAG = "CardsPresenter";

    @Inject
    WebService cardsService;
    @Inject
    DatabaseHelper databaseHelper;

    public CardsPresenter() {
        CardsApp.getAppComponent().inject(this);
        loadData();
    }

    public void onItemClick(@NonNull Card card) {
        getViewState().showDetails(card);
    }

    private void loadData() {

        cardsService.getCards()
                .doOnSubscribe(getViewState()::showLoading)
                .doOnTerminate(getViewState()::hideLoading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::showCards, throwable -> getViewState().showError());
    }

    public void addCard(Card card) {
        PrefUtils.insertToken();

        card.setId(String.valueOf(UUID.randomUUID()));
        try {
            databaseHelper.getCardDAO().create(card);
            Log.d(LOG_TAG, "created " + card);
        } catch (SQLException e) {
            Log.d(LOG_TAG, e.toString());
        }
    }
}
