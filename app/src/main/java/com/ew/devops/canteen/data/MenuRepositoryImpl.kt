package com.ew.devops.canteen.data

import android.content.SharedPreferences
import com.ew.devops.canteen.network.ApiResponse
import com.ew.devops.canteen.network.ContentMenu
import com.ew.devops.canteen.network.CulinaApiService
import io.reactivex.Observable

class MenuRepositoryImpl(private val preferences: SharedPreferences, private val culinaApiService: CulinaApiService) : IMenuRepository {

    override fun menu(date: String): Observable<ApiResponse<ContentMenu>> 
            = getApiToken(preferences).flatMapMaybe { token -> culinaApiService.getMenu(token, date) }


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
}