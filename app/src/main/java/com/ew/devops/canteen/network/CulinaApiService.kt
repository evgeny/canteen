package com.ew.devops.canteen.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CulinaApiService : CulinaApi {

    private val culinaApi: CulinaApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.qnips.com/cons/api")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        culinaApi = retrofit.create(CulinaApi::class.java)
    }

    override fun getMenu(): Call<String> {
        return culinaApi.getMenu()
    }

    override fun getNewIdentitiy(deviceType: String): Call<String> {
        return culinaApi.getNewIdentitiy(deviceType)
    }

}