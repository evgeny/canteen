package com.ew.devops.canteen

import android.app.Application
import com.ew.devops.canteen.di.*
import com.facebook.stetho.Stetho

class CanteenApplication : Application() {

    companion object {
        lateinit var appComponent: MenuContext
        lateinit var culinaService: CulinaService
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        appComponent = createContext()
        culinaService = buildCulinaService()
    }

    private fun buildCulinaService(): CulinaService {
        val networkModule = NetworkModule.Impl(this)
        val localStore = LocalStore.Impl(getSharedPreferences("api_key", 0))

        return CulinaService.Impl(networkModule, localStore)
    }

    private fun createContext() : MenuContext {
        val networkModule = NetworkModule.Impl(this)
        val localStore = LocalStore.Impl(getSharedPreferences("api_key", 0))
        val culinaService = CulinaService.Impl(networkModule, localStore)
        return MenuContext.Impl(culinaService, FirebaseModule.Impl())
    }
}