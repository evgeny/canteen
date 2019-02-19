package com.ew.devops.canteen.di

import com.ew.devops.canteen.CanteenApplication
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder().cache(cache).addNetworkInterceptor(StethoInterceptor()).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return  Retrofit.Builder()
                .client(httpClient)
                .baseUrl("https://api.qnips.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideResponseCache(context: CanteenApplication): Cache {
        val cacheSize = 5 * 1024 * 1024L // 10 MiB

        return Cache(context.cacheDir, cacheSize)
    }
}