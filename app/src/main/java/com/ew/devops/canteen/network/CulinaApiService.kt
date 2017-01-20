package com.ew.devops.canteen.network

import rx.Observable
import javax.inject.Inject

class CulinaApiService @Inject constructor(private val culinaApi: CulinaApi) : CulinaApi {

    override fun getMenu(authorization: String): Observable<ApiResponse<ContentMenu>> {
        return culinaApi.getMenu(authorization)
    }

    override fun getNewIdentitiy(deviceType: String): Observable<ApiResponse<ContentNewIdentity>> {
        return culinaApi.getNewIdentitiy(deviceType)
    }

    override fun getMenu(authorization: String, date: String): Observable<ApiResponse<ContentMenu>> {
        return culinaApi.getMenu(authorization, date)
    }
}