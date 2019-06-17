package com.ew.devops.canteen.di

import android.support.annotation.UiThread
import com.ew.devops.canteen.CanteenApplication
import com.ew.devops.canteen.network.ContentMenu
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * 
 */
class DayMenuPresenter(private val date: String): CoroutineScope, DisposableHandle {
    override fun dispose() {
        coroutineContext.cancelChildren()
    }

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    /**
     * TODO provide date via constructor
     */
    @UiThread
    fun menuObservable(): ContentMenu = runBlocking {
        val menuResponse = withContext(Dispatchers.IO) {
            CanteenApplication.registry.culinaService.menu(date)
        }
        
        menuResponse.Content
    }
}