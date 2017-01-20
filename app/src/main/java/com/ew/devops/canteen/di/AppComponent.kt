package com.ew.devops.canteen.di

import com.ew.devops.canteen.DayMenuFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {
    fun inject(dayMenuFragment: DayMenuFragment)
}
