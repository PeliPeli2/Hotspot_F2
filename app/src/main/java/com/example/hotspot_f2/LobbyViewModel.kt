package com.example.hotspot_f2

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.GeoPoint

class LobbyViewModel: ViewModel() {

    var title = mutableStateOf("")
    var description =  mutableStateOf("")
    var type =  mutableStateOf("")
    var checkins =  mutableStateOf(0)
    var image =  mutableStateOf(R.drawable.lars)
    var location = mutableStateOf(GeoPoint(0.0,0.0))


    var hotspot: MutableState<Hotspot?> = mutableStateOf(null)
    var checkedInUsers = mutableStateListOf<CheckedInUser>()
    var isCheckedIn: MutableState<Boolean> = mutableStateOf(false)

    fun checkIn(newHotspot: Hotspot) {
       // call db check in function

        //
        hotspot.value = newHotspot
        isCheckedIn.value = true
    }

    fun checkOut() {
        // call db check out function

        //
        hotspot.value = null
        isCheckedIn.value = false
    }

    fun getNumberOfCheckedInUsers(): Int = checkedInUsers.size

    fun getStringOfContents(): String {
        var res = ""
        checkedInUsers.forEach { res += "name: ${it.name}, age: ${it.age}\n" }
        return res
    }

    init {
        checkedInUsers.add(CheckedInUser("usertestid1", "Regina George", 17))
        checkedInUsers.add(CheckedInUser("usertestid2", "Karen Smith", 17))
        checkedInUsers.add(CheckedInUser("usertestid3", "Gretchen Wieners", 17))
    }

    // not used yet
    //var hotspot = mutableStateOf(Hotspot("","","",0,R.drawable.lars, GeoPoint(0.0,0.0)))

}