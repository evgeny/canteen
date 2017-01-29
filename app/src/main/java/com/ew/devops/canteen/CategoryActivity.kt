package com.ew.devops.canteen

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.ew.devops.canteen.presenter.MainActivityPresenter
import com.ew.devops.canteen.utils.UiUtils
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.content_category.*
import javax.inject.Inject


class CategoryActivity : BaseActivity() {

    @Inject lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CanteenApplication.appComponent.inject(this)

        setContentView(R.layout.activity_category)
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        toolbar.setBackgroundColor(UiUtils.getCategoryColor(presenter.category!!.Id))
        image.setImageResource(UiUtils.getCategoryDrawable(presenter.category!!.Id))

        title = presenter.category!!.Name

        publish.setOnClickListener { view ->
            // Write a message to the database
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("review")

            myRef.setValue("it was fantastic")
        }

//        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
