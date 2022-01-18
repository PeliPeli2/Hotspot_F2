package com.example.hotspot_f2.nav

import com.example.hotspot_f2.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Map : NavigationItem("map_screen", R.drawable.ic_baseline_map_24, "Map")
    object Profile : NavigationItem("profile_screen", R.drawable.ic_baseline_person_24, "Profile")
    object List : NavigationItem("list_screen", R.drawable.ic_baseline_format_list_bulleted_24, "Hotspots")
    object Lobby : NavigationItem("lobby_screen", R.drawable.ic_home, "Lobby")
    // object Profile : NavigationItem("profile", R.drawable.ic_profile, "Profile")
}