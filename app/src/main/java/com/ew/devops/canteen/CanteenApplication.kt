package com.ew.devops.canteen

import android.app.Application
import com.ew.devops.canteen.di.DependenciesRegistry
import com.facebook.stetho.Stetho

class CanteenApplication : Application() {

    companion object {
        lateinit var registry: DependenciesRegistry
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        registry = DependenciesRegistry.init(this)
    }
}