package com.ew.devops.canteen.network

import io.reactivex.Maybe
import io.reactivex.Observable

class CulinaApiService(private val culinaApi: CulinaApi) : CulinaApi {

    override fun getMenu(authorization: String): Observable<ApiResponse<ContentMenu>> {
        return culinaApi.getMenu(authorization)
    }

    override fun getNewIdentity(deviceType: String): Observable<ApiResponse<ContentNewIdentity>> {
        return culinaApi.getNewIdentity(deviceType)
    }

    override fun getMenu(authorization: String, date: String): Maybe<ApiResponse<ContentMenu>> {
        return culinaApi.getMenu(authorization, date)
    }
}