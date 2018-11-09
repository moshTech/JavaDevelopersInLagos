package com.mosh.javadevelopersinlagos.service;

import com.mosh.javadevelopersinlagos.model.UserList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiService {

    @GET("/search/users")
    Call<UserList> getUserList(@Query("q") String filter);

}
