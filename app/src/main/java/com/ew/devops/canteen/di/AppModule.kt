package com.ew.devops.canteen.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.ew.devops.canteen.CanteenApplication
import com.ew.devops.canteen.data.IMenuRepository
import com.ew.devops.canteen.data.MenuRepositoryImpl
import com.ew.devops.canteen.network.CulinaApi
import com.ew.devops.canteen.network.CulinaApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule(val app: CanteenApplication) {

    @Provides
    @Singleton
    fun provideApplication(): CanteenApplication = app

    @Provides
    @Singleton
    fun provideSharedPreferences() : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)
    
    @Provides
    @Singleton
    fun provideCulinaApi(retrofit: Retrofit): CulinaApi = retrofit.create(CulinaApi::class.java)

    @Provides
    fun provideMenuRepository(sharedPreferences: SharedPreferences, apiServices: CulinaApiService): IMenuRepository = MenuRepositoryImpl(sharedPreferences, apiServices)
}

