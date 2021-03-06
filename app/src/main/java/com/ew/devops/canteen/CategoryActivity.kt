package com.ew.devops.canteen

import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.ew.devops.canteen.dish.RatingFragment
import com.ew.devops.canteen.models.Review
import com.ew.devops.canteen.network.Product
import com.ew.devops.canteen.utils.UiUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.content_category.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Detail view of chosen dish
 */
class CategoryActivity : BaseActivity(), RatingFragment.ReviewInterface {

    override fun postReview(rating: Float, comment: String, dishId: Int) {
        writeNewReview(dishId.toString(), getUid(), comment, rating)
    }

    lateinit var dbRef: DatabaseReference

    private val reviewDateFormatter: SimpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_category)
//        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

//        val categoryColor = UiUtils.getCategoryColor(presenter.category!!.Id)
//        app_bar.setBackgroundResource(categoryColor)
//        toolbar_layout.setContentScrimResource(categoryColor)
//        setStatusBarColor(categoryColor)
//
//        val categoryDrawable = UiUtils.getCategoryDrawable(presenter.category!!.Id)
//        image.setImageResource(categoryDrawable)
//
//        product = presenter.category!!.Products[0]
//        title = presenter.category!!.Name
//        dish.text = product.Name
//
//        showProgressDialog()
//        val dishId = product.Id
//        dbRef.child("reviews").child("$dishId").addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("CategoryActivity", "Failed to read value.", error.toException())
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                hideProgressDialog()
//                // TODO if review was changed it's appear double in review feed
//                var averageRating = 0f
//                var childrenCount = 0
//                dataSnapshot.children.forEach {
//                    val review = it.getValue(Review::class.java)
//                    if (review != null) {
//                        Log.d("CategoryActivity", "Value is: " + review.text)
//                        appendReview(review)
//
//                        averageRating += review.rating
//                        childrenCount++
//                    }
//                }
//
//                rating.rating = averageRating / childrenCount
//            }
//        })

//        publish.setOnClickListener {
//            // Write a message to the database
//
//            val reviewBody = review.text.toString()
//            val rating = rating.rating
//            val dish = product.Id
//
//            writeNewReview(dish.toString(), getUid(), reviewBody, rating)
//        }

        fab.setOnClickListener { view ->
            val dialog = RatingFragment.newInstance(product.Id)
            dialog.show(fragmentManager, "RatingFragment")
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun writeNewReview(dishId: String, author: String, text: String, rating: Float) {
        val review = Review(dishId, author, text, rating, Date().time)
        val mapReview = review.toMap()
        dbRef.updateChildren(mapOf("/reviews/$dishId/$author" to mapReview))

    }

//    fun updateDish(id: String, title: String, price: Float = 0.0f, rating: Float) {
//        val dish = Dish(id, title, price, rating)
//        val valueMap = dish.toMap()
//    }

    /**
     * Fill and append a review to review feed
     * @param review review instance to display in review feed
     */
    fun appendReview(review: Review) {
        val reviewLayout = layoutInflater.inflate(R.layout.review_layout, review_layout, false)
        reviewLayout.findViewById<TextView>(R.id.name).text = review.author

        reviewLayout.findViewById<TextView>(R.id.date).text = reviewDateFormatter.format(Date(review.timestamp))
        reviewLayout.findViewById<TextView>(R.id.review).text = review.text
        reviewLayout.findViewById<RatingBar>(R.id.rating).rating = review.rating

        review_layout.addView(reviewLayout)
    }

    override fun inject() {
//        CanteenApplication.appComponent.menu()
    }
}
