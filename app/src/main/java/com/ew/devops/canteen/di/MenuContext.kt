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

// TODO remove this class
class Test {
    fun test() {

    }

    fun createContext(application: Application): Context {
        val localStore = LocalStore.Impl(application.getSharedPreferences("culina_api", 0))
        val networkModule = NetworkModule2.Impl(application)
        val culinaModule = CulinaModule.Impl(networkModule)
        val menuRepository = MenuRepository.Impl(culinaModule, localStore)

        return Context.Impl(menuRepository)
    }
}

interface Context : MenuRepository {
    class Impl(menuRepository: MenuRepository) : Context, MenuRepository by menuRepository
}

interface MenuRepository {
    fun menu(date: String): Observable<ApiResponse<ContentMenu>>

    class Impl(culinaModule: CulinaModule, private val localStore: LocalStore) : MenuRepository {
        val culinaApi by lazy { culinaModule.culinaApi }

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

//MODULES
//interface RepositoryModule {
//    val repository: IMenuRepository
//
//    class Impl : RepositoryModule {
//        override val repository by lazy { MenuRepositoryImpl() }
//    }
//}

interface CulinaModule {
    val culinaApi: CulinaApi

    class Impl(networkModule: NetworkModule2) : CulinaModule {
        override val culinaApi: CulinaApi by lazy { networkModule.retrofit.create(CulinaApi::class.java) }
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

interface NetworkModule2 {
    val cache: Cache
    val retrofit: Retrofit
    val okHttp: OkHttpClient

    class Impl(application: Application) : NetworkModule2 {
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