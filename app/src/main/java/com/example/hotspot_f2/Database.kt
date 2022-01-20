package com.example.hotspot_f2

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Database() {
    private companion object {
        private const val TAG = "FIRESTOREDB"
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

    /*fun private TESTDELETESOMEHOTSPOTS() {
        val db = Firebase.firestore
        db.collection("hotspots").document("BTpd9I2XTpsVSnWcJWgj")
            .delete()
            .addOnSuccessListener { Log.d("DBDELETETEST", "Deleted 1") }
            .addOnFailureListener { Log.d("DBDELETETEST", "Failed to delete 1") }

        db.collection("hotspots").document("ZGLyDKHUlPLkdIx8AIB4")
            .delete()
            .addOnSuccessListener { Log.d("DBDELETETEST", "Deleted 2") }
            .addOnFailureListener { Log.d("DBDELETETEST", "Failed to delete 2") }

        db.collection("hotspots").document("fd4qEWbWBtj66jugG8xY")
            .delete()
            .addOnSuccessListener { Log.d("DBDELETETEST", "Deleted 3") }
            .addOnFailureListener { Log.d("DBDELETETEST", "Failed to delete 3") }

        db.collection("hotspots").document("gKUHuYbBFRZ15tn15tVA")
            .delete()
            .addOnSuccessListener { Log.d("DBDELETETEST", "Deleted 4") }
            .addOnFailureListener { Log.d("DBDELETETEST", "Failed to delete 4") }

        db.collection("hotspots").document("rZhVUtBiRj5VlWoCQ8EH")
            .delete()
            .addOnSuccessListener { Log.d("DBDELETETEST", "Deleted 5") }
            .addOnFailureListener { Log.d("DBDELETETEST", "Failed to delete 1") }
    }*/

    fun FIXCHECKINCOUNT() {
        val db = Firebase.firestore
        db.collection("hotspots").document("7fNHoCmiYCM21IG4kgMC").update("checkins", 5)
        db.collection("hotspots").document("ghgmW1O1hTOn1lMrXW6Q").update("checkins", 1)
        db.collection("hotspots").document("q1jBYdzkGVjVkJfrXyIi").update("checkins", 1)
        db.collection("hotspots").document("rYPA7GSyCP912WcQ9Zo7").update("checkins", 3)
    }

    fun TESTPRINTALLHOTSPOTIDS() {
        val db = Firebase.firestore
        db.collection("hotspots")
            .get()
            .addOnSuccessListener { result ->
                Log.d("DBTESTPRINT", "**********************")
                for (document in result) {
                    Log.d("DBTESTPRINT", "ID: ${document.id} name: ${document.get("title").toString()}")
                }
                Log.d("DBTESTPRINT", "**********************")
            }
    }


    fun writeDummyHotspots() {
        getDummyHotspots().forEach {writeHotspot("Bente Bent", it) }
    }

    fun writeDummyUsers() {
        val testUser = ProfileViewModel()
        testUser.name.value = "Adam Abel"
        testUser.age.value = 99
        testUser.description.value = "En bruger af appen"
        testUser.imageID.value = R.drawable.lars
        updateUser(testUser.name.value, testUser)

        val testUser2 = ProfileViewModel()
        testUser2.name.value = "Bente Bent"
        testUser2.age.value = 88
        testUser2.description.value = "En anden bruger af appen"
        testUser2.imageID.value = R.drawable.lars
        updateUser(testUser2.name.value, testUser2)
    }

    fun testUpdateUser(dummyUserID: String, profileViewModel: ProfileViewModel) {
        updateUser(dummyUserID, profileViewModel)
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

    fun testjkl() {
        val db = Firebase.firestore

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

    fun testGetUser(userID: String, profileViewModel: ProfileViewModel) {
        getUser(userID = userID, profileViewModel = profileViewModel)
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
            "checkins" to hotspot.checkins,
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
        db.collection("hotspots")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
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

       db.collection("hotspots").addSnapshotListener { snapshots, error ->
           if(error != null) {
               Log.d(TAG, "ERROR NOT NULL")
               return@addSnapshotListener
           }
           if(snapshots != null) {
               Log.d(TAG, "IN IF STATEMENT")
               for (documentChange in snapshots!!.documentChanges) {
                   val hs = hotspotViewModel.hotspots.firstOrNull {it.id == documentChange.document.id}
                   hs?.checkins?.value = documentChange.document.get("checkins").toString().toInt()

                   // Log.d(TAG, "New checkins value: ${documentChange.document.get("checkins").toString().toInt()}")
               }
           }

       }
    }

    fun checkInTestUsers(lobbyViewModel: LobbyViewModel) {
        checkInUser(
            hotspotID = lobbyViewModel.hotspot.value!!.id,
            userID = "usertestid1",
            name = "Regina George",
            age = 17,
            lobbyViewModel = lobbyViewModel
        )

        checkInUser(
            hotspotID = lobbyViewModel.hotspot.value!!.id,
            userID = "usertestid2",
            name = "Karen Smith",
            age = 17,
            lobbyViewModel = lobbyViewModel
        )

        checkInUser(
            hotspotID = lobbyViewModel.hotspot.value!!.id,
            userID = "usertestid3",
            name = "Gretchen Wieners",
            age = 17,
            lobbyViewModel = lobbyViewModel
        )
    }

    fun checkInTestUsers2(lobbyViewModel: LobbyViewModel) {
        checkInUser(
            hotspotID = lobbyViewModel.hotspot.value!!.id,
            userID = "usertestid4",
            name = "Johnny Lawrence",
            age = 56,
            lobbyViewModel = lobbyViewModel
        )

        checkInUser(
            hotspotID = lobbyViewModel.hotspot.value!!.id,
            userID = "usertestid5",
            name = "Daniel LaRusso",
            age = 60,
            lobbyViewModel = lobbyViewModel
        )

        checkInUser(
            hotspotID = lobbyViewModel.hotspot.value!!.id,
            userID = "usertestid6",
            name = "John Kreese",
            age = 75,
            lobbyViewModel = lobbyViewModel
        )
    }

    fun checkOutCurrentUser(lobbyViewModel: LobbyViewModel) {
        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        //TODO: Make this a transaction
        val db = Firebase.firestore
        db.collection("hotspots").document(lobbyViewModel.hotspot.value!!.id).update("checkins", FieldValue.increment(-1))
            .addOnSuccessListener { /*lobbyViewModel.hotspot.value?.checkins = lobbyViewModel.hotspot.value!!.checkins - 1*/ }
            .addOnFailureListener { e -> Log.w(TAG, "Error incrementing checkins", e) }

        db.collection("hotspots")
            .document(lobbyViewModel.hotspot.value!!.id).
            collection("checked_in_users")
            .document(userID)
            .delete()
            .addOnSuccessListener {
                //TODO: move side effects to a lambda function that gets passed along
                lobbyViewModel.isCheckedIn.value = false
                lobbyViewModel.checkedInUsers.clear()
                Log.d(TAG, "Checked out user with id $userID")
            }
            .addOnFailureListener { Log.d(TAG, "Failed to check out user") }
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
            .addOnSuccessListener { /*lobbyViewModel.hotspot.value?.checkins = lobbyViewModel.hotspot.value!!.checkins + 1*/ }
            .addOnFailureListener { e -> Log.w(TAG, "Error incrementing checkins", e) }

        db.collection("hotspots").document(hotspotID).collection("checked_in_users").document(userID)
            .set(data)
            .addOnSuccessListener {
                    documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: $userID")
                    getCheckedInUsers(lobbyViewModel = lobbyViewModel)
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }

    /*fun OLDcheckInCurrentUser(profileViewModel: ProfileViewModel, lobbyViewModel: LobbyViewModel) {
        checkInUser(
            hotspotID = lobbyViewModel.hotspot.value!!.id,
            userID = FirebaseAuth.getInstance().currentUser!!.uid,
            name = profileViewModel.name.value,
            age = profileViewModel.age.value
        )
    }*/

    /*private fun OLDcheckInUser(hotspotID: String, userID: String, name: String, age: Int) {
        val data = hashMapOf(
            "name" to name,
            "age" to age
        )

        val db = Firebase.firestore
        db.collection("hotspots").document(hotspotID).collection("checked_in_users").document(userID)
            .set(data)
            .addOnSuccessListener {
                    documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: $userID")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }*/


    fun getCheckedInUsers(lobbyViewModel: LobbyViewModel) {

        if(lobbyViewModel.hotspot.value == null) {
            Log.d(TAG, "getCheckedInUsers: hotspot is null")
            return
        }

        val db = Firebase.firestore
        db.collection("hotspots").document(lobbyViewModel.hotspot.value!!.id).collection("checked_in_users")
            .get()
            .addOnSuccessListener { result ->
                //TODO: Move side effects to lambda function
                lobbyViewModel.isCheckedIn.value = true
                lobbyViewModel.checkedInUsers.clear()
                for(document in result) {
                    lobbyViewModel.checkedInUsers.add(
                        CheckedInUser(
                            id = document.id,
                            name = document.toObject(CheckedInUser::class.java)!!.name,
                            age = document.toObject(CheckedInUser::class.java)!!.age
                        )
                    )
                }
                Log.d("DBGETUSERS", lobbyViewModel.getStringOfContents())
            }
    }

    private fun hashMapFromHotspot(hs: Hotspot): HashMap<String, Comparable<*>> {
        return hashMapOf(
            "title" to hs.title,
            "description" to hs.description,
            "type" to hs.type,
            "checkins" to hs.checkins.value,
            "imageID" to hs.imageID,
            "location" to hs.location)
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
        else -> R.drawable.lars
    }
}