package com.ew.devops.canteen.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Review {
    var uid = ""
    var author = ""
    var text = ""

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Review.class)
    }

    constructor(uid: String, author: String, text: String) {
        this.uid = uid
        this.author = author
        this.text = text
    }

    /**
     * Convert class fields to key-values pair to persist it to firebase db
     */
    @Exclude
    fun toMap() = mapOf("dish" to uid, "author" to author, "text" to text)
}