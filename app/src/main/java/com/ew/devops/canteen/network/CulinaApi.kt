package com.ew.devops.canteen.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import rx.Observable

/**
 * https://api.qnips.com/cons/api/Stores/20943/Menu
 */
interface CulinaApi {
    @Headers("Accept: application/json", "App-Feature-Set: 1793")
    @GET("cons/api/Stores/20943/Menu")
    fun getMenu(@Header("Authorization") authorization: String): Observable<String>

    /**
     * https://api.qnips.com/cons/api/NewIdentity?deviceType=Android+6.0.1
     */
    @Headers("Accept: application/json", "App-Feature-Set: 1793")
    @GET("cons/api/NewIdentity")
    fun getNewIdentitiy(@Query("deviceType") deviceType: String): Observable<ApiResponse>
}