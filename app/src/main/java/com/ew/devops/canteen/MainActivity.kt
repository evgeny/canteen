package com.ew.devops.canteen

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ew.devops.canteen.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = MainActivityPresenter()

        if (getPreferences(Context.MODE_PRIVATE).getString("api_token", "").isEmpty()) {
            presenter.requestNewIdentity()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ response ->
                        run {
                            text.text = response.Content.ApiKey
                            getPreferences(Context.MODE_PRIVATE).edit().putString("api_token", response.Content.ApiKey).apply()
                        }
                    }, {
                        e -> Log.e("TAG", "", e)
                    })
        }
    }
}
