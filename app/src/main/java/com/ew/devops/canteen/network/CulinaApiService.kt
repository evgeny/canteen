package com.ew.devops.canteen.network

import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject

class CulinaApiService @Inject constructor(private val culinaApi: CulinaApi) : CulinaApi {

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