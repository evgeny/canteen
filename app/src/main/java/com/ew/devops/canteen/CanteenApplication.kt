package com.ew.devops.canteen

import android.app.Application
import com.facebook.stetho.Stetho

class CanteenApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}