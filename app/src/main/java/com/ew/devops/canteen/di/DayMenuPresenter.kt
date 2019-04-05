package com.ew.devops.canteen.di

import com.ew.devops.canteen.data.IMenuRepository
import com.ew.devops.canteen.network.ContentMenu
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DayMenuPresenter {

    @Inject
    lateinit var menuRepo: IMenuRepository

    fun menuObservable(date: String): Observable<ContentMenu> {
        return menuRepo.menu(date)
                .map { response -> response.Content }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}