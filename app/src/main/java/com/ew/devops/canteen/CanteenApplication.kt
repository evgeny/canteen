package com.ew.devops.canteen

import android.app.Application
import com.ew.devops.canteen.di.AppComponent
import com.ew.devops.canteen.di.AppModule
import com.ew.devops.canteen.di.DaggerAppComponent
import com.facebook.stetho.Stetho

class CanteenApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}