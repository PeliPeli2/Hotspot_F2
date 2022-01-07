package com.example.hotspot_f2

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirestoreRegistrar
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Database() {
    private companion object {
        private const val TAG = "FIRESTOREDB"
    }

    fun getHotspots(): List<Hotspot> {
        return getDummyHotspots() //TODO: get hotspots from Firestore database
    }

    private fun getDummyHotspots(): List<Hotspot> {

        val point1 = Hotspot(
            title="Brønnum",
            description = "Adjacent to the buzzing Kongens Nytorv square is Brønnum, a hushed," +
                    " historic retreat—the perfect post-sightseeing reprieve. Specializing in Champagne," +
                    " along with a well-edited list of wine and cocktails and late-afternoon live jazz performances.",
            type = "Bar",
            checkins = 30,
            imageID = R.drawable.broennum,
            location = GeoPoint(55.73,12.3962))

        val point2 = Hotspot(
            title="Duck And Cover",
            description = "Duck and Cover is a subterranean sanctuary in bustling Vesterbro. " +
                    "In contrast to the neighborhood’s other packed-to-the-gills bars, it's a relaxed, " +
                    "ideal place to pop in for a subdued nightcap amid a striking collection of vintage furniture and lighting.",
            type = "Bar",
            checkins = 10,
            imageID = R.drawable.duck_and_cover,
            location = GeoPoint(55.73,12.39))

        val point3 = Hotspot(
            title="Ørsted",
            type = "Bar",
            description = "Copenhagen's craft beer scene is impressive, and one of the best places to become acquainted with it is Ørsted Ølbar," +
                    " located a stone’s throw from Nørreport Station and tranquil Ørstedparken.",
            checkins = 23,
            imageID = R.drawable.oersted,
            location = GeoPoint(55.7250,12.3910))

        val point4 = Hotspot(
            title="K-bar",
            type = "Bar",
            description = "K-bar is named for Kirsten Holm, " +
                    "a pioneer of the Copenhagen cocktail scene. Years after opening, this bar, " +
                    "situated near Højbro Square, remains a sought-after drinks destination",
            checkins = 44,
            imageID = R.drawable.bar,
            location = GeoPoint(55.7279,12.39))

        return listOf(point1, point2, point3, point4)
    }

    fun writeDummyHotspots() {
        getDummyHotspots().forEach { writeHotspot(it) }
    }

    fun testUpdateUser()
    {
        val testUser = User("Adam Abel", 99, "En bruger af appen")
        updateUser(testUser)

        val testUser2 = User("Bente Bent", 98, "En anden bruger af appen")
        updateUser(testUser2)
    }

    fun testGetUser()
    {
        val testUser = getUser("Bente Bent")
    }


    fun updateUser(user: User)
    {
        Firebase.firestore.collection("users").document(user.name).set(hashMapFromUser(user))
    }

    private fun fetchUser(name: String)
    {
        val docRef = Firebase.firestore.collection("users").document(name)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    User.user.name = document.get("name").toString()
                    User.user.description = document.get("description").toString()
                    User.user.age = document.get("age").toString().toInt()
                    Log.d(TAG, "Found a user named ${User.user.name}")}
            }
            .addOnFailureListener { Log.d(TAG, "Failed to get user") }
    }

    fun getUser(name: String): User {
        fetchUser(name)
        return User.user
    }

    /*
    fun getUser(name: String): User{

        val resultUser = User("", 0, "")

        val docRef = Firebase.firestore.collection("users").document(name)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    resultUser.name = document.get("name").toString()
                    resultUser.description = document.get("description").toString()
                    resultUser.age = document.get("age").toString().toInt()
                    Log.d(TAG, "Found a user named ${resultUser.name}")}
            }
            .addOnFailureListener { Log.d(TAG, "Failed to get user") }

        Log.d(TAG, "Retrieved a user named ${resultUser.name}")
        return resultUser
    }
     */

    fun writeHotspot(hotspot: Hotspot) {
        val data = hashMapOf(
            "title" to hotspot.title,
            "description" to hotspot.description,
            "type" to hotspot.type,
            "checkins" to hotspot.checkins,
            "imageID" to hotspot.imageID,
            "location" to hotspot.location)

        val db = Firebase.firestore

        db.collection("hotspots")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun hashMapFromHotspot(hs: Hotspot): HashMap<String, Comparable<*>> {
        return hashMapOf(
            "title" to hs.title,
            "description" to hs.description,
            "type" to hs.type,
            "checkins" to hs.checkins,
            "imageID" to hs.imageID,
            "location" to hs.location)
    }

    fun hashMapFromUser(user: User): HashMap<String, Comparable<*>> {
        return hashMapOf(
            "name" to user.name,
            "age" to user.age,
            "description" to user.description)
    }

    fun documentToHotspot()
    {}

    fun writeTestData() {
    // Create a new user with a first and last name


        val testHotspot = Hotspot(
            title = "Testbar",
            description = "En bar et sted i verden",
            type = "bar",
            checkins = 12,
            imageID = R.drawable.bar,
            location = GeoPoint(22.0, 22.0))

        val testHashMap = hashMapFromHotspot(testHotspot)


        // Add a new document with a generated ID
        val db = Firebase.firestore
        db.collection("users")
            .add(testHashMap)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    private fun writeTestDataOld() {
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
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}