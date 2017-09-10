package com.soulfly.englishcards.dagger;

import android.content.Context;

import com.soulfly.englishcards.app.CardsApp;
import com.soulfly.englishcards.cards.presenters.ServerSortedPresenter;
import com.soulfly.englishcards.cards.presenters.CardsPresenter;
import com.soulfly.englishcards.cards.presenters.InsertCardPresenter;
import com.soulfly.englishcards.cards.presenters.UserCardsPresenter;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by Elena on 18.03.2017.
 */
@Singleton
@Component(modules = {ContextModule.class, DaoModule.class, PassModule.class,
        ServiceModule.class})
public interface AppComponent {
     Context getContext();
    //WebService getService();
    //Bus getBus();

    //void inject(SignInPresenter presenter);
    void inject(CardsPresenter cardsPresenter);

    void inject(ServerSortedPresenter cardsByThemePresenter);

    void inject(InsertCardPresenter newUserPresenter);

    void inject(UserCardsPresenter userCardsPresenter);

    void inject(CardsApp cardsApp);

}
