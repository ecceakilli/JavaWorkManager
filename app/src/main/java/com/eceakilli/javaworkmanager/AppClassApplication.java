package com.eceakilli.javaworkmanager;

import android.app.Application;

public class AppClassApplication extends Application {
    static AppClassApplication instance;
    RestInterface restInterface;

    @Override
    public void onCreate() {
        super.onCreate();

        instance =this;
        restInterface= ApiClient.getClient().create(RestInterface.class);


    }
    public synchronized static AppClassApplication getInstance(){

        return instance;
    }


    //singletn(instance) kullanammızn nedeni 2 kere restint. kullanamazsın

    public RestInterface getRestInterface() {
        return restInterface;
    }
}
