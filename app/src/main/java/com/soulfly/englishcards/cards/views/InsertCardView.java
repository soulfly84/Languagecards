package com.soulfly.englishcards.cards.views;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.soulfly.englishcards.general.LoadingView;
import com.soulfly.englishcards.model.Card;

/**
 * Created by Elena on 19.03.2017.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface InsertCardView extends MvpView, LoadingView, ErrorView {

    void showSuccess(@NonNull Card card);
}
