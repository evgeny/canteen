package com.ew.devops.canteen.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Dish {
    var id = ""
    var title = ""
    var price = 0.0f
    var rating = 0.0f

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Review.class)
    }

    constructor(id: String, title: String, price: Float, rating: Float) {
        this.id = id
        this.title = title
        this.price = price
        this.rating = rating
    }

    /**
     * Convert class fields to key-values pair to persist it to firebase db
     */
    @Exclude
    fun toMap() = mapOf("id" to id, "title" to title, "price" to price, "rating" to rating)
}