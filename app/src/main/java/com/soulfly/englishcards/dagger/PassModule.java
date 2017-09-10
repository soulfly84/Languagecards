package com.soulfly.englishcards.dagger;

import com.soulfly.englishcards.api.WebApi;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//для передачи данных...
@Module
public class PassModule {
    @Provides
    @Singleton
    public Bus provideBus(WebApi api) {
        return new Bus();
    }
}
