package com.ew.devops.canteen

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set action bar
        setSupportActionBar(toolbar)

        tabs.tabMode = TabLayout.MODE_SCROLLABLE

//        val presenter = MainActivityPresenter()

//        presenter.getMenu(getPreferences(Context.MODE_PRIVATE)).subscribe({ response ->
//            Log.d("TAG", "menu response=" + response)
//            text.text = response.Content.Categories[0].Products[0].Name
//        }, { error -> Log.e("TAG", "", error) })

        val adapter = DayMenuAdapter(supportFragmentManager)
        pager.adapter = adapter

        val tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(pager)
    }
}
