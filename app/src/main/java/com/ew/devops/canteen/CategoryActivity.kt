package com.ew.devops.canteen

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.ew.devops.canteen.models.Review
import com.ew.devops.canteen.presenter.MainActivityPresenter
import com.ew.devops.canteen.utils.UiUtils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.content_category.*
import javax.inject.Inject


class CategoryActivity : BaseActivity() {

    @Inject lateinit var presenter: MainActivityPresenter

    var database: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CanteenApplication.appComponent.inject(this)

        setContentView(R.layout.activity_category)
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        toolbar_layout.setBackgroundColor(UiUtils.getCategoryColor(presenter.category!!.Id))
//        toolbar.setBackgroundColor(UiUtils.getCategoryColor(presenter.category!!.Id))
        image.setImageResource(UiUtils.getCategoryDrawable(presenter.category!!.Id))


        title = presenter.category!!.Name

        database = FirebaseDatabase.getInstance().reference
//        database?.child("reviews")?.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError?) {
//                Log.w("TAG", "Failed to read value.", error?.toException())
////                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot?) {
//                val value = dataSnapshot?.getValue(HashMap::class.java)
//                Log.d("TAG", "Value is: " + value)
//            }
//
//        })

        publish.setOnClickListener { view ->
            // Write a message to the database

            val reviewBody = review.text.toString()
            val reviewId = presenter.category!!.Id

            writeNewReview(reviewId.toString(), getUid(), reviewBody)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun writeNewReview(dishId: String, author: String, text: String) {
        database?.let {
            val key = it.child("reviews").push().key
            val review = Review(dishId, author, text)

//            childUpdates.put("/reviews/" + key, review.toMap())
//            childUpdates.put("/user-posts/$userId/$key", postValues)

            it.updateChildren(mapOf("/reviews/" + key to review.toMap()))
        }
    }
}
