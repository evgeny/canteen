package com.ew.devops.canteen.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import rx.Observable

interface CulinaApi {
    /**
     * https://api.qnips.com/cons/api/Stores/20943/Menu
     * GET /cons/api/Stores/20924/Menu HTTP/1.1
     * Accept: application/json
     * Accept-Charset: UTF-8
     * Accept-Language: en-US
     * User-Agent: Primus/2.0.4 (Android 6.0 Build/MASTER; unknown Android SDK built for x86_64)
     * App-Feature-Set: 1793
     * App-Brand: Primus
     * Authorization: 00b2b8e39a2d37fb886ed5301031aed23a
     * Host: api.qnips.com
     * Connection: Keep-Alive
     * Accept-Encoding: gzip
     * If-None-Match: "2b78a8338a5bafcdea6caa7b85f14b58"
     *
     * curl -H 'Accept: application/json' -H 'Accept-Charset: UTF-8' -H 'Accept-Language: en-US'
     * -H 'User-Agent: Primus/2.0.4 (Android 6.0 Build/MASTER; unknown Android SDK built for x86_64)'
     * -H 'App-Feature-Set: 1793' -H 'App-Brand: Primus'
     * -H 'Authorization: 0076f99b67c097928724a5f82bc4aaf86e'
     * -H 'Host: api.qnips.com' --compressed 'https://api.qnips.com/cons/api/Stores/20924/Menu'
     *
     * curl -H 'Accept: application/json' -H 'Accept-Charset: UTF-8' -H 'Accept-Language: en-US' -H 'User-Agent: Primus/2.0.4 (Android 7.1 Build/NPF26K; unknown Android SDK built for x86_64)' -H 'App-Feature-Set: 1793' -H 'App-Brand: Primus' -H 'Authorization: 00e05bd5fb5ec8f6a1f5008dc25332c538' -H 'Host: api.qnips.com' --compressed 'https://api.qnips.com/cons/api/Stores/20924/Menu'
     */
    @Headers("Accept: application/json", "App-Feature-Set: 1793")
    @GET("cons/api/Stores/20924/Menu")
    fun getMenu(@Header("Authorization") authorization: String): Observable<ApiResponse<ContentMenu>>

    @Headers("Accept: application/json", "App-Feature-Set: 1793")
    @GET("cons/api/Stores/20924/Menu")
    fun getMenu(@Header("Authorization") authorization: String, @Query("date") date: String): Observable<ApiResponse<ContentMenu>>

    /**
     * https://api.qnips.com/cons/api/NewIdentity?deviceType=Android+6.0.1
     *
     * curl -H 'Accept: application/json' https://api.qnips.com/cons/api/NewIdentity?deviceType=Android+6.0.1

     */
    @Headers("Accept: application/json", "App-Feature-Set: 1793")
    @GET("cons/api/NewIdentity")
    fun getNewIdentitiy(@Query("deviceType") deviceType: String): Observable<ApiResponse<ContentNewIdentity>>

    //https://104.47.143.161/cons/api/v2/Master?since=1457002509060
//    GET /cons/api/Stores/20943/Menu HTTP/1.1
//    Accept: application/json
//    Accept-Charset: UTF-8
//    Accept-Language: en-US
//    User-Agent: Primus/2.0.4 (Android 6.0 Build/MASTER; unknown Android SDK built for x86_64)
//    App-Feature-Set: 1793
//    App-Brand: Primus
//    Authorization: 00ac7c74a22bc75ea26ad34f984df44dff
//    Host: api.qnips.com
//    Connection: Keep-Alive
//    Accept-Encoding: gzip

}