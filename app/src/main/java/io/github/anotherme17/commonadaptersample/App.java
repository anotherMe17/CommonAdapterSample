package io.github.anotherme17.commonadaptersample;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user798 on 2016/12/29.
 */

public class App extends Application {
    private static App sInstance;
    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://7xk9dj.com1.z0.glb.clouddn.com/adapter/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static App getInstance() {
        return sInstance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
