package com.ew.devops.canteen.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CulinaApiService : CulinaApi {

    private val culinaApi: CulinaApi

    init {
        val okhttp = OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()
        val retrofit = Retrofit.Builder()
                .client(okhttp)
                .baseUrl("https://api.qnips.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        culinaApi = retrofit.create(CulinaApi::class.java)
    }

    override fun getMenu(): Call<String> {
        return culinaApi.getMenu()
    }

    override fun getNewIdentitiy(deviceType: String): Call<ApiResponse> {
        return culinaApi.getNewIdentitiy(deviceType)
    }

}