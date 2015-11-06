package com.example.noworries.breweryeventapp.api;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class EventRestClient {

    public static String BASE_URL;

    public EventAPI getApi() {
        BASE_URL = "https://pacific-caverns-2433.herokuapp.com";
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return restAdapter.create(EventAPI.class);
    }
}
