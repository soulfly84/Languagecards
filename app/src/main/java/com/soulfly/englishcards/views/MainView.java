package com.soulfly.englishcards.views;

import com.soulfly.englishcards.model.Card;

import java.util.List;

public interface MainView {
    void showCounters(List<Card> cards);

    void showLoading();

    void showEmpty();
}
