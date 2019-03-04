package com.ew.devops.canteen.presenter

import android.content.SharedPreferences
import com.ew.devops.canteen.network.ApiResponse
import com.ew.devops.canteen.network.Category
import com.ew.devops.canteen.network.ContentMenu
import com.ew.devops.canteen.network.CulinaApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivityPresenter @Inject constructor(private var culinaApiService: CulinaApiService) {

    var category: Category? = null

    private fun getApiToken(prefs: SharedPreferences): Observable<String> {
        val token = prefs.getString("api_token", "")
        return if (token.isEmpty()) {
            culinaApiService.getNewIdentity("Android+6.0.1").map { response ->
                "0" + response.Content.ApiKey
            }.doOnNext { t -> prefs.edit().putString("api_token", t).apply() }
        } else {
            Observable.just(token)
        }
    }

    fun getMenu(prefs: SharedPreferences, date: String): Observable<ApiResponse<ContentMenu>> {
        return getApiToken(prefs).flatMap { token -> culinaApiService.getMenu(token, date) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}