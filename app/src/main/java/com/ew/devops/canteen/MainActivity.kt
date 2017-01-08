package com.ew.devops.canteen

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ew.devops.canteen.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = MainActivityPresenter()

//        presenter.getApiToken(getPreferences(Context.MODE_PRIVATE)).subscribe({ token -> text.text = token },
//                { error -> Log.e("TAG", "", error) })

        presenter.getMenu(getPreferences(Context.MODE_PRIVATE)).subscribe({ response ->
                Log.d("TAG", "menu response=" + response)
                text.text = response.Content.Categories[0].Products[0].Name
        }, { error -> Log.e("TAG", "", error) })
    }
}
