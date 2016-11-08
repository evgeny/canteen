package com.ew.devops.canteen.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.Observable

class CulinaApiService : CulinaApi {

    private val culinaApi: CulinaApi

    init {
        val okhttp = OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()
        val retrofit = Retrofit.Builder()
                .client(okhttp)
                .baseUrl("https://api.qnips.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        culinaApi = retrofit.create(CulinaApi::class.java)
    }

    override fun getMenu(authorization: String): Observable<String> {
        return culinaApi.getMenu(authorization)
    }

    override fun getNewIdentitiy(deviceType: String): Observable<ApiResponse> {
        return culinaApi.getNewIdentitiy(deviceType)
    }

}