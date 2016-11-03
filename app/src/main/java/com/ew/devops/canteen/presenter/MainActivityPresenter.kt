package com.ew.devops.canteen.presenter

import android.content.SharedPreferences
import com.ew.devops.canteen.network.ApiResponse
import com.ew.devops.canteen.network.CulinaApiService
import rx.Observable

class MainActivityPresenter(private var culinaApiService: CulinaApiService = CulinaApiService()) {

    fun requestNewIdentity(): Observable<ApiResponse> {
        return Observable.create({
            subscriber ->
            val response = culinaApiService.getNewIdentitiy("Android+6.0.1").execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        })
    }

    fun getApiToken(prefs: SharedPreferences): Observable<String> {
        var token = prefs.getString("api_token", "")
        if (token.isEmpty()) {

        } else {
            return token
        }
    }
}