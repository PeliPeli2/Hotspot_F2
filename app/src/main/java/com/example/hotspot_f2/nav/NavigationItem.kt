package com.example.hotspot_f2.nav

import com.example.hotspot_f2.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_baseline_map_24, "Map")
    object Music : NavigationItem("Profile", R.drawable.ic_baseline_person_24, "Profile")
    object Movies : NavigationItem("List", R.drawable.ic_baseline_chat_bubble_24, "List")
    object Books : NavigationItem("books", R.drawable.ic_home, "Books")
    // object Profile : NavigationItem("profile", R.drawable.ic_profile, "Profile")
}