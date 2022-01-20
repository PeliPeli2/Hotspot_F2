package com.example.hotspot_f2

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.example.hotspot_f2.nav.NavigationItem
import com.example.hotspot_f2.ui.CustomInfoWindow
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.google.firebase.firestore.GeoPoint
import java.io.IOException
import java.util.*


val locationArrayList1: MutableList<String> = ArrayList()
val locationArrayList2: MutableList<String> = ArrayList()
val locationArrayList3: MutableList<String> = ArrayList()
var confirm: Boolean = false

fun testAddingAHotspot(addressInput: String, titleInput: String, descriptionInput: String, hotspotViewModel: HotspotViewModel, context: Context)
{

    locationArrayList1.add(addressInput);
    locationArrayList2.add(titleInput);
    locationArrayList3.add(descriptionInput);

    val locName = getLocationFromAddress(locationArrayList1[locationArrayList1.size-1], context)
    if(locName != null) {
        hotspotViewModel.addNewHotspot(
            Hotspot(
                title = locationArrayList2.last(),
                description = locationArrayList3.last(),
                location = GeoPoint(locName.latitude, locName.longitude)
            )
        )
    }
    //confirm = false
    Log.d("ADDTEST", "ADDED HOTSPOT USING BUTTON I HOPE")
    //********************************

/*    locationArrayList1.add(addressInput);
    locationArrayList2.add(titleInput);
    locationArrayList3.add(descriptionInput);*/
    //confirm = true

}

@Composable
fun Hotspotmap(
    hotspotViewModel: HotspotViewModel,
    lobbyViewModel: LobbyViewModel,
    navController: NavHostController,
    modifier:Modifier=Modifier,
    OnReady:(GoogleMap)->Unit
)
{
    val context= LocalContext.current
    val markers = mutableListOf<MarkerOptions>()

    val cameraPosition = CameraPosition.builder()
        .target(LatLng(55.7314, 12.3962))
        .zoom(15f)
        .build()

    val mapView= remember {
        MapView(context)
    }

    val lifecycle= LocalLifecycleOwner.current.lifecycle

    lifecycle.addObserver(rememberMapLifecycle(mapView))

    AndroidView(
        factory = {
                  mapView.apply {
                      mapView.getMapAsync { googleMap->
                          googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

/*
                          if (confirm) {
                              Log.d("LOCTEST", locationArrayList1[locationArrayList1.size-1].toString())
                              val locName = getLocationFromAddress(locationArrayList1[locationArrayList1.size-1], context)
                              if(locName != null) {
                                  hotspotViewModel.addNewHotspot(
                                      Hotspot(
                                          title = locationArrayList2.last(),
                                          description = locationArrayList3.last(),
                                          location = GeoPoint(locName.latitude, locName.longitude)
                                      )
                                  )
                              }
                              confirm = false
                              Log.d("ADDTEST", "ADDED HOTSPOT USING BUTTON I HOPE")
                          }
*/

                          hotspotViewModel.hotspots.forEach {
                              markers.add(
                                  MarkerOptions()
                                      .position(
                                          com.google.android.gms.maps.model.LatLng(
                                              it.location.latitude,
                                              it.location.longitude
                                          )
                                      )
                                      .title(it.title)
                                      .snippet(it.description)
                              )
                          }

                          markers.forEach { googleMap.addMarker(it) }
                          googleMap.setInfoWindowAdapter(CustomInfoWindow(this.context))

                          googleMap.setOnInfoWindowClickListener { markerOptions ->
                              lobbyViewModel.hotspot.value = hotspotViewModel.hotspots.firstOrNull { it.title == markerOptions.title}

                              when(markerOptions.title) {
                                  "Brønnum" -> lobbyViewModel.image.value = R.drawable.broennum
                                  "Duck And Cover" -> lobbyViewModel.image.value = R.drawable.duck_and_cover
                                  "Ørsted" -> lobbyViewModel.image.value = R.drawable.oersted
                                  "K-bar" -> lobbyViewModel.image.value = R.drawable.k_bar
                                  else -> lobbyViewModel.image.value = R.drawable.broennum
                              }

                              // necessary to prevent error when navigating from map
                              GlobalScope.launch(Dispatchers.Main) {
                                  navController.popBackStack()
                                  navController.navigate(NavigationItem.Lobby.route)
                              }
                          }
                          googleMap.setOnMarkerClickListener { markerOptions ->
                              if (markerOptions.isInfoWindowShown) {
                                  markerOptions.hideInfoWindow()

                              } else {
                                  markerOptions.showInfoWindow()
                              }
                              true
                          }
                          OnReady(googleMap)
                }
            }
        },
        modifier=modifier
    )
}


fun getLocationFromAddress(strAddress: String?, context: Context ): GeoPoint? {
    val coder = Geocoder(context)
    val address: List<Address>
    var p1: GeoPoint? = null
    try {
        address = coder.getFromLocationName(strAddress, 5)
        if (address == null) {
            return null
        }
        val location: Address = address[0]
        location.getLatitude()
        location.getLongitude()
        p1 = GeoPoint(
            (location.getLatitude()) as Double,
            (location.getLongitude()) as Double
        )
        return p1
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}


@Composable
fun rememberMapLifecycle(map:MapView):LifecycleObserver{
    return remember{
        LifecycleEventObserver { lifecycleOwner, event ->
            when(event){
                Lifecycle.Event.ON_CREATE -> map.onCreate(Bundle())
                Lifecycle.Event.ON_START -> map.onStart()
                Lifecycle.Event.ON_RESUME -> map.onResume()
                Lifecycle.Event.ON_PAUSE -> map.onPause()
                Lifecycle.Event.ON_STOP -> map.onStop()
                Lifecycle.Event.ON_DESTROY -> map.onDestroy()
                Lifecycle.Event.ON_ANY -> throw IllegalStateException()
            }
        }
    }

}