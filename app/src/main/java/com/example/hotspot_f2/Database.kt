package com.example.hotspot_f2

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.zip.CheckedInputStream

class Database() {
    private companion object {
        private const val TAG = "FIRESTOREDB"
    }
@Composable
    fun getHotspots(): List<HotspotData> {
        return getDummyHotspots() //TODO: get hotspots from Firestore database
    }
@Composable
    private fun getDummyHotspots(): List<HotspotData> {

        val point1 = HotspotData(
            title="Brønnum",
            description = "Adjacent to the buzzing Kongens Nytorv square is Brønnum, a hushed," +
                    " historic retreat—the perfect post-sightseeing reprieve. Specializing in Champagne," +
                    " along with a well-edited list of wine and cocktails and late-afternoon live jazz performances.",
            location = GeoPoint(55.73,12.3962),
            "Kongens Nytorv",
            connections = 3, painterResource(id = R.drawable.broennum),"Bar",10 )

        val point2 = HotspotData(
            title="Duck And Cover",
            description = "Duck and Cover is a subterranean sanctuary in bustling Vesterbro. " +
                    "In contrast to the neighborhood’s other packed-to-the-gills bars, it's a relaxed, " +
                    "ideal place to pop in for a subdued nightcap amid a striking collection of vintage furniture and lighting.",
            location = GeoPoint(55.73,12.39),"Vesterbro",
            connections = 7, painterResource(id = R.drawable.duck_and_cover),"Bar",5 )

        val point3 = HotspotData(
            title="Ørsted",
            description = "Copenhagen's craft beer scene is impressive, and one of the best places to become acquainted with it is Ørsted Ølbar," +
                    " located a stone’s throw from Nørreport Station and tranquil Ørstedparken.",
            location = GeoPoint(55.7250,12.3910), "København K",
            connections = 12,painterResource(id = R.drawable.oersted),"Bar",30 )

        val point4 = HotspotData(
            title="K-bar",
            description = "K-bar is named for Kirsten Holm, " +
                    "a pioneer of the Copenhagen cocktail scene. Years after opening, this bar, " +
                    "situated near Højbro Square, remains a sought-after drinks destination",
            location = GeoPoint(55.7279,12.39), "København K",
            connections = 34,painterResource(id = R.drawable.bar),"Bar",40 )

        return listOf(point1, point2, point3, point4)
    }
@Composable
    fun writeDummyHotspots() {
        getDummyHotspots().forEach { writeHotspot(it) }
    }


    fun writeHotspot(hotspot: HotspotData) {
        val data = hashMapOf(
            "title" to hotspot.title,
            "description" to hotspot.description,
            "location" to hotspot.location,
            "connections" to hotspot.connections)

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
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}

data class HotspotData(
    val title: String,
    val description: String,
    val location: GeoPoint,
    val locationname: String,
    val connections: Int,
    val image: Painter,
    val type: String,
    val checkedins: Int)

