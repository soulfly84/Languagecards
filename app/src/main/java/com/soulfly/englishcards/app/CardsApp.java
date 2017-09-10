package com.soulfly.englishcards.app;

import android.app.Application;

import com.soulfly.englishcards.dagger.AppComponent;
import com.soulfly.englishcards.dagger.ContextModule;
import com.soulfly.englishcards.dagger.DaggerAppComponent;

/**
 * Created by Elena on 18.08.2017.
 */
public class CardsApp extends Application {
	private static AppComponent sAppComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		sAppComponent = DaggerAppComponent.builder()
				.contextModule(new ContextModule(this))
				.build();
	}

	public static AppComponent getAppComponent() {
		return sAppComponent;
	}
}
