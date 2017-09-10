package com.soulfly.englishcards.cards.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.soulfly.englishcards.general.LoadingView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CardsView extends MvpView, LoadingView, ErrorView,CardsBaseView {

}
