package ng.codeimpact.javadevelopersinlagos.service;

import ng.codeimpact.javadevelopersinlagos.model.UserList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiService {

    @GET("/search/users")
    Call<UserList> getUserList(@Query("q") String filter);

}
