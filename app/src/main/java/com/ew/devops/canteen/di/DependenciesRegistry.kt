package com.ew.devops.canteen.di

import android.app.Application

object DependenciesRegistry {

    private lateinit var appContext: Application

    private val networkModule: NetworkModule by lazy { NetworkModule.Impl(appContext) }
    private val localStore: LocalStore by lazy { LocalStore.Impl(appContext.getSharedPreferences("api_key", 0)) }
    
    val culinaService: CulinaService by lazy { CulinaService.Impl(networkModule, localStore) }
    val firebaseModule: FirebaseModule by lazy { FirebaseModule.Impl() }

    /**
     * init registery
     */
    fun init(application: Application) {
        appContext = application
    }
}