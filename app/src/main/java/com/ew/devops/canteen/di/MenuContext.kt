package com.ew.devops.canteen.di

import android.app.Application
import android.content.SharedPreferences
import com.ew.devops.canteen.network.ApiResponse
import com.ew.devops.canteen.network.ContentMenu
import com.ew.devops.canteen.network.CulinaApi
import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Observable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface MenuContext : CulinaService, FirebaseModule {
    
    class Impl(culinaService: CulinaService, firebaseModule: FirebaseModule)
        : MenuContext, CulinaService by culinaService,
            FirebaseModule by firebaseModule
}

interface CulinaService {
    fun menu(date: String): Observable<ApiResponse<ContentMenu>>

    class Impl(networkModule: NetworkModule, private val localStore: LocalStore) : CulinaService {
        val culinaApi: CulinaApi by lazy { networkModule.retrofit.create(CulinaApi::class.java) }

        override fun menu(date: String): Observable<ApiResponse<ContentMenu>> {
            return getApiToken().flatMapMaybe { token -> culinaApi.getMenu(token, date) }
        }

        private fun getApiToken(): Observable<String> {
            val token = localStore.apiKey
            return if (token.isEmpty()) {
                culinaApi.getNewIdentity("Android+6.0.1").map { response ->
                    "0" + response.Content.ApiKey
                }.doOnNext { t -> localStore.apiKey = t }
            } else {
                Observable.just(token)
            }
        }
    }
}

interface LocalStore {
    var apiKey: String

    class Impl(val sharedPreferences: SharedPreferences) : LocalStore {
        override var apiKey: String
            get() = sharedPreferences.getString("api_token", "")
            set(value) {
                sharedPreferences.edit().putString("api_token", value).apply()
            }
    }
}

interface NetworkModule {
    val cache: Cache
    val retrofit: Retrofit
    val okHttp: OkHttpClient

    class Impl(application: Application) : NetworkModule {
        override val cache = Cache(application.cacheDir, 5 * 1024 * 1024L)
        override val okHttp: OkHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(StethoInterceptor())
                .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                .build()

        override val retrofit: Retrofit =
                Retrofit.Builder()
                        .client(okHttp)
                        .baseUrl("https://api.qnips.com/")
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(MoshiConverterFactory.create())
                        .build()

    }
}