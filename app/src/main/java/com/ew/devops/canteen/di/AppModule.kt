package com.ew.devops.canteen.di

import com.ew.devops.canteen.CanteenApplication
import com.ew.devops.canteen.network.CulinaApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule(val app: CanteenApplication) {

    @Provides
    @Singleton
    fun provideApplication(): CanteenApplication {
        return app
    }

    @Provides
    @Singleton
    fun provideCulinaApi(retrofit: Retrofit): CulinaApi {
        return retrofit.create(CulinaApi::class.java)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirebaseDb(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }
}

