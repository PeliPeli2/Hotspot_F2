package com.example.hotspot_f2

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Database() {
    fun writeTestData() {
// Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Bente",
            "last" to "Bent",
            "born" to 1990
        )

// Add a new document with a generated ID
        val db = Firebase.firestore
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("SUCCESS", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("FEJL", "Error adding document", e)
            }
    }
}


