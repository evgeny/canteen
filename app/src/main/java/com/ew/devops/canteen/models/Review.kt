package com.ew.devops.canteen.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Review {
    var dish = ""
    var author = ""
    var text = ""
    var rating = 0.0f
    var timestamp: Long = 0L

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Review.class)
    }

    constructor(dish: String, author: String, text: String, rating: Float, timestamp: Long) {
        this.dish = dish
        this.author = author
        this.text = text
        this.rating = rating
        this.timestamp = timestamp
    }

    /**
     * Convert class fields to key-values pair to persist it to firebase db
     */
    @Exclude
    fun toMap() = mapOf("dish" to dish, "author" to author, "text" to text, "rating" to rating, "timestamp" to timestamp)
}