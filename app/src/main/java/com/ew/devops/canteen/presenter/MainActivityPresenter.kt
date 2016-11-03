package com.ew.devops.canteen.presenter

import android.content.SharedPreferences
import com.ew.devops.canteen.network.CulinaApiService
import rx.Observable

class MainActivityPresenter(private var culinaApiService: CulinaApiService = CulinaApiService()) {

    fun getApiToken(prefs: SharedPreferences): Observable<String> {
        val token = prefs.getString("api_token", "")
        if (token.isEmpty()) {
            return culinaApiService.getNewIdentitiy("Android+6.0.1").map({
                response ->
                response.Content.ApiKey
            }).doOnNext({ token -> prefs.edit().putString("api_token", token).apply() })
        } else {
            return Observable.just(token)
        }
    }
}