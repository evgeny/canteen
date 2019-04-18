package com.ew.devops.canteen.di

import com.ew.devops.canteen.CanteenApplication
import com.ew.devops.canteen.network.ContentMenu
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 
 */
class DayMenuPresenter(private val date: String) {

    /**
     * TODO provide date via constructor
     */
    fun menuObservable(): Observable<ContentMenu> {
        return CanteenApplication.registry.culinaService.menu(date)
                .map { response -> response.Content }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

}