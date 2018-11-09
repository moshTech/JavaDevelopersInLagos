package com.mosh.javadevelopersinlagos.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiBuilder {

    private static final String BASE_URL = "https://api.github.com";

    private Retrofit retrofit;

    public RestApiBuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public com.mosh.javadevelopersinlagos.service.RestApiService getService() {
        return retrofit.create(com.mosh.javadevelopersinlagos.service.RestApiService.class);
    }
}