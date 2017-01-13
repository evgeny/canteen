package com.ew.devops.canteen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ew.devops.canteen.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal var adapter: DayMenuAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val presenter = MainActivityPresenter()

//        presenter.getMenu(getPreferences(Context.MODE_PRIVATE)).subscribe({ response ->
//            Log.d("TAG", "menu response=" + response)
//            text.text = response.Content.Categories[0].Products[0].Name
//        }, { error -> Log.e("TAG", "", error) })

        val adapter = DayMenuAdapter(supportFragmentManager)
        pager.adapter = adapter
    }
}
