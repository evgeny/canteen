package com.ew.devops.canteen.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface CulinaApiJ {

    @Headers({"Accept: application/json",
            "App-Feature-Set: 1793", "App-Brand: Primus", "Host: api.qnips.com", "Accept-Charset: UTF-8", "User-Agent: Primus/2.0.4"})
    @GET("/Stores/20943/Menu")
    Call<String> getMenu(@Header("Authorization") String authorization);

    /**
     * https://api.qnips.com/cons/api/NewIdentity?deviceType=Android+6.0.1
     */
    @Headers({"Accept: application/json",
            "App-Feature-Set: 1793", "App-Brand: Primus", "Host: api.qnips.com", "Accept-Charset: UTF-8", "User-Agent: Primus/2.0.4"})
    @GET("/NewIdentity")
    Call<String> getNewIdentitiy(@Query("deviceType") String deviceType);
}
