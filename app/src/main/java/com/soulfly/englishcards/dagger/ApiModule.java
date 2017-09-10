package com.soulfly.englishcards.dagger;

import com.soulfly.englishcards.api.WebApi;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Elena on 18.03.2017.
 */
@Module(includes = {RetrofitModule.class})
public class ApiModule {
	@Provides
	@Singleton
	public WebApi provideApi(Retrofit retrofit) {
		return retrofit.create(WebApi.class);
	}
}
