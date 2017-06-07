package com.ew.devops.canteen

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.TextView
import com.ew.devops.canteen.models.Dish
import com.ew.devops.canteen.models.Review
import com.ew.devops.canteen.presenter.MainActivityPresenter
import com.ew.devops.canteen.utils.UiUtils
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.content_category.*
import java.util.*
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

        showProgressDialog()
        database = FirebaseDatabase.getInstance().reference
        val dishId = presenter.category!!.Id
        database?.child("reviews")?.child("$dishId")?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                Log.w("TAG", "Failed to read value.", error?.toException())
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                hideProgressDialog()
                dataSnapshot?.children?.forEach {
                    val review = it.getValue(Review::class.java)
                    Log.d("TAG", "Value is: " + review.text)
                    appendReview(review)
                }
            }
        })

        publish.setOnClickListener { view ->
            // Write a message to the database

            val reviewBody = review.text.toString()
            val rating = rating.rating
            val reviewId = presenter.category!!.Id

            writeNewReview(reviewId.toString(), getUid(), reviewBody, rating)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun writeNewReview(dishId: String, author: String, text: String, rating: Float) {
        database?.let {
//            val key = it.child("reviews").push().key
            val review = Review(dishId, author, text, rating, Date().time)
//            val dish = Dish()

//            childUpdates.put("/reviews/" + key, review.toMap())
//            childUpdates.put("/user-posts/$userId/$key", postValues)

            val mapReview = review.toMap()

//            it.updateChildren(mapOf("/reviews/" + key to review.toMap()))
//            it.updateChildren(mapOf("/dish-reviews/$dishId" to mapReview, "/user-reviews/$author" to mapReview ))
            it.updateChildren(mapOf("/reviews/$dishId/$author" to mapReview))
        }
    }

    fun updateDish(id: String, title: String, price: Float = 0.0f, rating: Float) {
        database?.let {
            val dish = Dish(id, title, price, rating)
            val valueMap = dish.toMap()
//            it.updateChildren()
        }
    }

    fun appendReview(review: Review) {
        val reviewView: TextView = TextView(this)
        reviewView.text = review.text
        review_layout.addView(reviewView)
    }
}
