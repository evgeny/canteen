package com.ew.devops.canteen.di

import com.ew.devops.canteen.DayMenuFragment
import com.ew.devops.canteen.CategoryActivity
import com.ew.devops.canteen.SignInActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, FirebaseModule::class))
interface AppComponent {
    fun inject(dayMenuFragment: DayMenuFragment)
    fun inject(categoryActivity: CategoryActivity)
    fun inject(signInActivity: SignInActivity)
}
