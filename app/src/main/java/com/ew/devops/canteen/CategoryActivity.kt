package com.ew.devops.canteen

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.ew.devops.canteen.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_category.*
import javax.inject.Inject

class CategoryActivity : BaseActivity() {

    @Inject lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CanteenApplication.appComponent.inject(this)

        setContentView(R.layout.activity_category)
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        title = presenter.category!!.Name

//        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
