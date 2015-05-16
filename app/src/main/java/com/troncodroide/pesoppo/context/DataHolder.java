package com.troncodroide.pesoppo.context;

import android.app.Application;

/**
 * Created by Tronco on 13/05/2015.
 */
public class DataHolder extends Application {

    private static DataHolder application;

    public static Application getApplication(){
        if (application == null){
            application = new DataHolder();
        }
        return application;
    }

}
