package com.ew.devops.canteen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ew.devops.canteen.network.ApiResponse
import com.ew.devops.canteen.network.CulinaApiService
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val culinaApiService = CulinaApiService()

        Observable.create(Observable.OnSubscribe<ApiResponse> {
            subscriber ->
            val response = culinaApiService.getNewIdentitiy("Android+6.0.1").execute()

            if (response.isSuccessful) {
                subscriber.onNext(response.body())
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        .subscribe ({
            response -> text.text = response.Content.ApiKey
        }, {
            e -> Log.e("TAG", "", e)
        })
    }
}
