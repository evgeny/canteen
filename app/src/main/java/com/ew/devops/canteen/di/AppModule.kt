package com.ew.devops.canteen.di

import com.ew.devops.canteen.CanteenApplication
import com.ew.devops.canteen.network.CulinaApi
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
}

