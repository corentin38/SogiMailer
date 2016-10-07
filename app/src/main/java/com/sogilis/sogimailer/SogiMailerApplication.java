package com.sogilis.sogimailer;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

import dagger.ObjectGraph;

public class SogiMailerApplication extends Application {

	private ObjectGraph objectGraph;

	public static Context ctx;

	@Override
	public void onCreate() {
		super.onCreate();
		this.ctx = getApplicationContext();

	    Stetho.initializeWithDefaults(this);
		objectGraph = ObjectGraph.create(new SogiMailerModule());
	}

	public ObjectGraph getObjectGraph() {
		return objectGraph;
	}

}
