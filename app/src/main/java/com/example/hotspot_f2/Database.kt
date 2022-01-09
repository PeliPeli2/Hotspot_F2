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

    fun getHotspots(hotspotViewModel: HotspotViewModel) {
        //TODO: get hotspots from Firestore database

        getDummyHotspots().forEach {
            hotspotViewModel.hotspots.add(it)
        }
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
        val testUser = ProfileViewModel()
        testUser.name.value = "Adam Abel"
        testUser.age.value = 99
        testUser.description.value = "En bruger af appen"
        testUser.imageID.value = R.drawable.lars
        updateUser(testUser)

        val testUser2 = ProfileViewModel()
        testUser2.name.value = "Bente Bent"
        testUser2.age.value = 88
        testUser2.description.value = "En anden bruger af appen"
        testUser2.imageID.value = R.drawable.lars
        updateUser(testUser2)
    }

    fun updateUser(profileViewModel: ProfileViewModel)
    {
        Firebase.firestore.collection("users").document(profileViewModel.name.value).set(hashMapFromProfile(profileViewModel))
            .addOnSuccessListener { Log.d(TAG, "Updated the user ${profileViewModel.name.value}" ) }
            .addOnFailureListener { Log.d(TAG, "Error updating the user ${profileViewModel.name.value}" )}
    }

    private fun fetchUser(name: String, profileViewModel: ProfileViewModel)
    {
        val docRef = Firebase.firestore.collection("users").document(name)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    profileViewModel.name.value = document.get("name").toString()
                    profileViewModel.description.value = document.get("description").toString()
                    profileViewModel.age.value = document.get("age").toString().toInt()
                    profileViewModel.imageID.value = document.get("imageID").toString().toInt()
                    Log.d(TAG, "Fetched a user named ${profileViewModel.name.value}")}
            }
            .addOnFailureListener { Log.d(TAG, "Failed to get user") }
    }

    fun getUser(requestedUserName: String, profileViewModel: ProfileViewModel) {
        fetchUser(requestedUserName, profileViewModel)
    }

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

    fun hashMapFromProfile(profileViewModel: ProfileViewModel): HashMap<String, Comparable<*>> {
        return hashMapOf(
            "name" to profileViewModel.name.value,
            "age" to profileViewModel.age.value,
            "description" to profileViewModel.description.value,
            "imageID" to profileViewModel.imageID.value)
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

}