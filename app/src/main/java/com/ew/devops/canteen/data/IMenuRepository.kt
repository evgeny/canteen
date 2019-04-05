package com.ew.devops.canteen.data

import com.ew.devops.canteen.network.ApiResponse
import com.ew.devops.canteen.network.ContentMenu
import io.reactivex.Observable

interface IMenuRepository {
    fun menu(date: String) : Observable<ApiResponse<ContentMenu>>
}