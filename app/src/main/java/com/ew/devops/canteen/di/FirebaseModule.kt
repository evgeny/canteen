package com.ew.devops.canteen.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

interface FirebaseModule {
    val firebaseAuth: FirebaseAuth
    val firebaseDb: DatabaseReference

    class Impl : FirebaseModule {
        override val firebaseDb by lazy { FirebaseDatabase.getInstance().reference }
        override val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    }
}