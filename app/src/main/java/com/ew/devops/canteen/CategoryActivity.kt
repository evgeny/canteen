package com.ew.devops.canteen

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.RatingBar
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

    @Inject lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_category)
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        toolbar_layout.setBackgroundColor(UiUtils.getCategoryColor(presenter.category!!.Id))
//        toolbar.setBackgroundColor(UiUtils.getCategoryColor(presenter.category!!.Id))
        image.setImageResource(UiUtils.getCategoryDrawable(presenter.category!!.Id))


        title = presenter.category!!.Name

        showProgressDialog()
        val dishId = presenter.category!!.Id
        dbRef.child("reviews")?.child("$dishId")?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                Log.w("TAG", "Failed to read value.", error?.toException())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                hideProgressDialog()
                // TODO if review was changed it's appear double in review feed
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
        val review = Review(dishId, author, text, rating, Date().time)
        val mapReview = review.toMap()
        dbRef.updateChildren(mapOf("/reviews/$dishId/$author" to mapReview))

    }

    fun updateDish(id: String, title: String, price: Float = 0.0f, rating: Float) {
        val dish = Dish(id, title, price, rating)
        val valueMap = dish.toMap()

    }

    /**
     * Fill and append a review to review feed
     * @param review review instance to display in review feed
     */
    fun appendReview(review: Review) {
        val reviewLayout = layoutInflater.inflate(R.layout.review_layout, review_layout, false)
        (reviewLayout.findViewById(R.id.name) as TextView).text = review.author

        // TODO parse date string from timestamp(millis)
        (reviewLayout.findViewById(R.id.date) as TextView).text = review.timestamp.toString()
        (reviewLayout.findViewById(R.id.review) as TextView).text = review.text
        (reviewLayout.findViewById(R.id.rating) as RatingBar).rating = review.rating

        review_layout.addView(reviewLayout)
    }

    override fun inject() {
        CanteenApplication.appComponent.inject(this)
    }
}
