package com.ew.devops.canteen.presenter

import android.content.SharedPreferences
import com.ew.devops.canteen.network.ApiResponse
import com.ew.devops.canteen.network.ContentMenu
import com.ew.devops.canteen.network.CulinaApiService
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivityPresenter(private var culinaApiService: CulinaApiService = CulinaApiService()) {

    fun getApiToken(prefs: SharedPreferences): Observable<String> {
        val token = prefs.getString("api_token", "")
        if (token.isEmpty()) {
            return culinaApiService.getNewIdentitiy("Android+6.0.1").map({
                response ->
                "0" + response.Content.ApiKey
            }).doOnNext({ token -> prefs.edit().putString("api_token", token).apply() })
        } else {
            return Observable.just(token)
        }
    }

    fun getMenu(prefs: SharedPreferences): Observable<ApiResponse<ContentMenu>> {
        return getApiToken(prefs).flatMap({token -> culinaApiService.getMenu(token)})
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
    }

    fun getMenu(prefs: SharedPreferences, date: String): Observable<ApiResponse<ContentMenu>> {
        return getApiToken(prefs).flatMap({token -> culinaApiService.getMenu(token, date)})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}