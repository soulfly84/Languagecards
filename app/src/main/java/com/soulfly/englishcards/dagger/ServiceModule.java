package com.soulfly.englishcards.dagger;

import com.soulfly.englishcards.api.WebService;
import com.soulfly.englishcards.api.WebApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Elena on 18.03.2017.
 */

@Module(includes = {ApiModule.class})
public class ServiceModule {
	@Provides
	@Singleton
	public WebService provideService(WebApi webApi) {
		return new WebService(webApi);
	}
}