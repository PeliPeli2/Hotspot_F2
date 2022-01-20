package com.example.hotspot_f2

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Database() {
    private companion object {
        private const val TAG = "FIRESTOREDB"
    }

    fun addHotspot(hotspot: Hotspot) {
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        writeHotspot(hotspot = hotspot, creator = userID)
    }

    fun getHotspots(hotspotViewModel: HotspotViewModel) {
        getAllHotspots(hotspotViewModel)
        //getDummyHotspots().forEach { hotspotViewModel.hotspots.add(it) }
    }

    private fun getDummyHotspots(): List<Hotspot> {
        val point1 = Hotspot(
            id = "dummyid1",
            title="Brønnum",
            description = "Adjacent to the buzzing Kongens Nytorv square is Brønnum, a hushed," +
                    " historic retreat—the perfect post-sightseeing reprieve. Specializing in Champagne," +
                    " along with a well-edited list of wine and cocktails and late-afternoon live jazz performances.",
            type = "Bar",
            checkins = mutableStateOf(30),
            imageID = R.drawable.broennum,
            location = GeoPoint(55.73,12.3962))

        val point2 = Hotspot(
            id = "dummyid2",
            title="Duck And Cover",
            description = "Duck and Cover is a subterranean sanctuary in bustling Vesterbro. " +
                    "In contrast to the neighborhood’s other packed-to-the-gills bars, it's a relaxed, " +
                    "ideal place to pop in for a subdued nightcap amid a striking collection of vintage furniture and lighting.",
            type = "Bar",
            checkins = mutableStateOf(10),
            imageID = R.drawable.duck_and_cover,
            location = GeoPoint(55.73,12.39))

        val point3 = Hotspot(
            id = "dummyid3",
            title="Ørsted",
            type = "Bar",
            description = "Copenhagen's craft beer scene is impressive, and one of the best places to become acquainted with it is Ørsted Ølbar," +
                    " located a stone’s throw from Nørreport Station and tranquil Ørstedparken.",
            checkins = mutableStateOf(23),
            imageID = R.drawable.oersted,
            location = GeoPoint(55.7250,12.3910))

        val point4 = Hotspot(
            id = "dummyid4",
            title="K-bar",
            type = "Bar",
            description = "K-bar is named for Kirsten Holm, " +
                    "a pioneer of the Copenhagen cocktail scene. Years after opening, this bar, " +
                    "situated near Højbro Square, remains a sought-after drinks destination",
            checkins = mutableStateOf(44),
            imageID = R.drawable.k_bar,
            location = GeoPoint(55.7279,12.39))

        return listOf(point1, point2, point3, point4)
    }

    fun updateCurrentUser(profileViewModel: ProfileViewModel) {
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        if(userID != null) {
            updateUser(userID, profileViewModel)
        }
    }

    private fun updateUser(userID: String, profileViewModel: ProfileViewModel) {
        Firebase.firestore.collection("users").document(userID).set(hashMapFromProfile(profileViewModel))
            .addOnSuccessListener { Log.d(TAG, "Updated the user ${profileViewModel.name.value}" ) }
            .addOnFailureListener { Log.d(TAG, "Error updating the user ${profileViewModel.name.value}" )}
    }

    private fun getUser(userID: String, profileViewModel: ProfileViewModel) {
        val docRef = Firebase.firestore.collection("users").document(userID)
        //val docRef = Firebase.firestore.collection("users").document("TESTING")
        docRef.get()
            .addOnSuccessListener { document ->
                if (!document.getData().isNullOrEmpty()) {
                    profileViewModel.name.value = document.get("name").toString()
                    profileViewModel.description.value = document.get("description").toString()
                    profileViewModel.age.value = document.get("age").toString().toInt()
                    profileViewModel.imageID.value = document.get("imageID").toString().toInt()
                    profileViewModel.firstLogin.value = "NORMAL"
                    Log.d(TAG, "Fetched user with userID $userID")}
                else {
                    profileViewModel.firstLogin.value = "FIRST_LOGIN"
                    Log.d(TAG, "No data for user with userID $userID")
                }
            }
            .addOnFailureListener { Log.d(TAG, "Failed to get user") }
    }

    fun getCurrentUser(profileViewModel: ProfileViewModel) {
        val userID = FirebaseAuth.getInstance().currentUser?.uid
        if(userID != null) {
            getUser(userID, profileViewModel)
        }
    }

    private fun writeHotspot(creator: String, hotspot: Hotspot) {
        val data = hashMapOf(
            "creator" to creator,
            "title" to hotspot.title,
            "description" to hotspot.description,
            "type" to hotspot.type,
            "checkins" to hotspot.checkins.value,
            "imageID" to hotspot.imageID,
            "location" to hotspot.location)

        val db = Firebase.firestore
        db.collection("hotspots")
            .add(data)
            .addOnSuccessListener { documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}") }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }

   private fun getAllHotspots(hotspotViewModel: HotspotViewModel) {
        hotspotViewModel.hotspots.clear()

        val db = Firebase.firestore
/*
        db.collection("hotspots")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("ADDTEST", "ADDING HOTSPOT LOCATION A ID IS ${document.id}")
                    hotspotViewModel.hotspots.add(
                        Hotspot(
                            id = document.id,
                            title = document.get("title").toString(),
                            description = document.get("description").toString(),
                            type = document.get("type").toString(),
                            checkins = mutableStateOf(document.get("checkins").toString().toInt()),
                            imageID = getDummyHotspotImageIDFromTitle(document.get("title").toString()), //TODO: actually use the imageID
                            location = document.get("location", GeoPoint::class.java)!!
                        )
                    )
                }
            }
*/


       db.collection("hotspots").addSnapshotListener { snapshots, error ->
           if(error != null) {
               Log.w(TAG, error)
               return@addSnapshotListener
           }
           if(snapshots != null) {
               for (documentChange in snapshots.documentChanges) {
                   when(documentChange.type) {
                       DocumentChange.Type.ADDED -> {
                           hotspotViewModel.hotspots.add(
                               Hotspot(
                                   id = documentChange.document.id,
                                   title = documentChange.document.get("title").toString(),
                                   description = documentChange.document.get("description").toString(),
                                   type = documentChange.document.get("type").toString(),
                                   checkins = mutableStateOf(documentChange.document.get("checkins").toString().toInt()),
                                   imageID = getDummyHotspotImageIDFromTitle(documentChange.document.get("title").toString()), //TODO: actually use the imageID
                                   location = documentChange.document.get("location", GeoPoint::class.java)!!
                               )
                           )
                           Log.d("ADDTEST", "ADDING HOTSPOT LOCATION B ID IS ${documentChange.document.id} L: ${hotspotViewModel.hotspots.last().location.latitude} L: ${hotspotViewModel.hotspots.last().location.longitude}")
                       }
                       DocumentChange.Type.REMOVED -> {
                           hotspotViewModel.hotspots.removeIf { it.id == documentChange.document.id }
                       }
                       DocumentChange.Type.MODIFIED -> {
                           val hs = hotspotViewModel.hotspots.firstOrNull {it.id == documentChange.document.id}
                           hs?.checkins?.value = documentChange.document.get("checkins").toString().toInt()
                       }
                   }
                   val hs = hotspotViewModel.hotspots.firstOrNull {it.id == documentChange.document.id}
                   hs?.checkins?.value = documentChange.document.get("checkins").toString().toInt()
               }
           }
       }
    }

    fun checkOutCurrentUser(lobbyViewModel: LobbyViewModel) {
        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        //TODO: Make this a transaction
        val db = Firebase.firestore

        //decrement the checkins field of the hotspot
        db.collection("hotspots").document(lobbyViewModel.checkedInID.value!!).update("checkins", FieldValue.increment(-1))
            .addOnSuccessListener { Log.d(TAG, "Decremented checkins") }
            .addOnFailureListener { e -> Log.w(TAG, "Error decrementing checkins", e) }

        db.collection("hotspots")
            .document(lobbyViewModel.checkedInID.value!!)
            .collection("checked_in_users")
            .document(userID)
            .delete()
            .addOnSuccessListener {
                //TODO: move side effects to a lambda function that gets passed along
                lobbyViewModel.isCheckedIn.value = false
                lobbyViewModel.checkedInID.value = null
                lobbyViewModel.checkedInUsers.clear()
                lobbyViewModel.busy = false
                Log.d(TAG, "Checked out user with id $userID")
            }
            .addOnFailureListener {
                lobbyViewModel.busy = false
                Log.d(TAG, "Failed to check out user")
            }
    }

    fun checkInCurrentUser(profileViewModel: ProfileViewModel, lobbyViewModel: LobbyViewModel) {
        checkInUser(
            hotspotID = lobbyViewModel.hotspot.value!!.id,
            userID = FirebaseAuth.getInstance().currentUser!!.uid,
            name = profileViewModel.name.value,
            age = profileViewModel.age.value,
            lobbyViewModel = lobbyViewModel
        )
    }

    private fun checkInUser(hotspotID: String, userID: String, name: String, age: Int, lobbyViewModel: LobbyViewModel) {
        val data = hashMapOf(
            "name" to name,
            "age" to age
        )

        val db = Firebase.firestore

        //TODO: Make this a transaction
        db.collection("hotspots").document(hotspotID).update("checkins", FieldValue.increment(1))
            .addOnSuccessListener { Log.d(TAG, "Incremented checkins") }
            .addOnFailureListener { e -> Log.w(TAG, "Error incrementing checkins", e) }

        db.collection("hotspots").document(hotspotID).collection("checked_in_users").document(userID)
            .set(data)
            .addOnSuccessListener {
                    documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: $userID")
                    getCheckedInUsers(lobbyViewModel = lobbyViewModel)
            }
            .addOnFailureListener {
                e -> Log.w(TAG, "Error adding document", e)
                lobbyViewModel.busy = false
            }
    }

    fun getCheckedInUsers(lobbyViewModel: LobbyViewModel) {

        if(lobbyViewModel.hotspot.value == null) {
            Log.d(TAG, "getCheckedInUsers: hotspot is null")
            return
        }

        lobbyViewModel.checkedInUsers.clear()

        val db = Firebase.firestore
        db.collection("hotspots").document(lobbyViewModel.hotspot.value!!.id).collection("checked_in_users")
            .get()
            .addOnSuccessListener { result ->
                lobbyViewModel.checkedInID.value = lobbyViewModel.hotspot.value!!.id
                lobbyViewModel.isCheckedIn.value = true
                lobbyViewModel.busy = false
            }

        // Listen for users checking in and out of this particular hotspot
        lobbyViewModel.listenerRegistration = db.collection("hotspots")
            .document(lobbyViewModel.hotspot.value!!.id)
            .collection("checked_in_users")
            .addSnapshotListener { snapshots, error ->
            if(error != null) {
                Log.w(TAG, error)
                return@addSnapshotListener
            }
            if(snapshots != null) {
                for (documentChange in snapshots.documentChanges) {
                    Log.d("ASDTEST", "GETTING USERS FROM LOCATION 2 DOCID: ${documentChange.document.id}")
                    when (documentChange.type) {
                        DocumentChange.Type.ADDED -> {
                            lobbyViewModel.checkedInUsers.add(
                                CheckedInUser(
                                    id = documentChange.document.id,
                                    name = documentChange.document.toObject(CheckedInUser::class.java).name,
                                    age = documentChange.document.toObject(CheckedInUser::class.java).age
                                )
                            )
                        }
                        DocumentChange.Type.REMOVED -> {
                            lobbyViewModel.checkedInUsers.removeIf { it.id == documentChange.document.id }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun hashMapFromProfile(profileViewModel: ProfileViewModel): HashMap<String, Comparable<*>> {
        return hashMapOf(
            "name" to profileViewModel.name.value,
            "age" to profileViewModel.age.value,
            "description" to profileViewModel.description.value,
            "imageID" to profileViewModel.imageID.value)
    }
}

fun getDummyHotspotImageIDFromTitle(title: String): Int {
    return when (title) {
        "Brønnum" -> R.drawable.broennum
        "Duck And Cover" -> R.drawable.duck_and_cover
        "Ørsted" -> R.drawable.oersted
        "K-bar" -> R.drawable.k_bar
        else -> R.drawable.broennum
    }
}