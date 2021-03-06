package com.soulfly.englishcards.cards.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.soulfly.englishcards.cards.views.CardsView;
import com.soulfly.englishcards.app.CardsApp;
import com.soulfly.englishcards.api.WebService;
import com.soulfly.englishcards.dao.DatabaseHelper;
import com.soulfly.englishcards.model.Card;
import com.soulfly.englishcards.utils.PrefUtils;

import java.sql.SQLException;
import java.util.UUID;

import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Elena on 18.03.2017.
 */
@InjectViewState
public class ServerSortedPresenter extends MvpPresenter<CardsView> {

    @Inject
    WebService cardsService;
    @Inject
    DatabaseHelper databaseHelper;

    public ServerSortedPresenter() {
        CardsApp.getAppComponent().inject(this);
    }

    public void init(String theme) {
                 cardsService.getCardsByTheme(theme)
                .doOnSubscribe(getViewState()::showLoading)
                .doOnTerminate(getViewState()::hideLoading)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::showCards, throwable -> getViewState().showError());
    }

    public void onItemClick(@NonNull Card card) {
        getViewState().showDetails(card);
    }

    public void addCard(Card card) {
        PrefUtils.insertToken();

        card.setId(String.valueOf(UUID.randomUUID()));
        try {
            databaseHelper.getCardDAO().create(card);
        } catch (SQLException e) {
            Log.d("SQLException ", e.toString());
        }
    }
}