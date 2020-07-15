package com.example.jonalnb.notas;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by JonaLNB on 29/04/2017.
 */

public class AppParse extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
    }
}
