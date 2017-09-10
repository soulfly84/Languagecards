package com.soulfly.englishcards.dagger;

import android.content.Context;

import com.soulfly.englishcards.dao.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Elena on 30.03.2017.
 */

@Module
public class DaoModule {

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper(Context context) {
        return new DatabaseHelper(context);
    }
}
