package com.example.trabinerson.moviefiend;

import android.app.Application;

/**
 * Manages singletons of the Movie Fiend application.
 */
public class MovieFiendApplication extends Application {

    public void onCreate() {
        super.onCreate();
        RequestQueueSingleton.init(this);
    }

}
