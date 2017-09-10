package com.soulfly.englishcards.cards.views;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.soulfly.englishcards.model.Card;

import java.util.List;

/**
 * Created by Elena on 20.03.2017.
 */

public interface CardsBaseView extends MvpView {

    void showCards(@NonNull List<Card> cards);

    void showDetails(@NonNull Card card);
}
