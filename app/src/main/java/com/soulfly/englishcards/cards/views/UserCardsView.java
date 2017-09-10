package com.soulfly.englishcards.cards.views;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.soulfly.englishcards.general.LoadingView;
import com.soulfly.englishcards.model.Card;


/**
 * Created by Elena on 20.03.2017.
 */

public interface UserCardsView extends MvpView, ErrorView, CardsBaseView,LoadingView, SuccessView{

    void showSuccess(@NonNull Card card);
}
