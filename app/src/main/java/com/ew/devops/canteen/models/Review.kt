package com.ew.devops.canteen.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Review {
    var dish = ""
    var author = ""
    var text = ""

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Review.class)
    }

    constructor(dish: String, author: String, text: String) {
        this.dish = dish
        this.author = author
        this.text = text
    }

    /**
     * Convert class fields to key-values pair to persist it to firebase db
     */
    @Exclude
    fun toMap() = mapOf("dish" to dish, "author" to author, "text" to text)
}