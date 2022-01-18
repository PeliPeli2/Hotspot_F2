package com.example.hotspot_f2

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.example.hotspot_f2.ui.CustomInfoWindow
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.GeoPoint
import java.io.IOException
import java.util.*

val locationArrayList1: MutableList<String> = ArrayList()
val locationArrayList2: MutableList<String> = ArrayList()
val locationArrayList3: MutableList<String> = ArrayList()
var confirm: Boolean = false

fun testAddingAHotspot(textState1: String, textState2: String, textState3: String)
{
    locationArrayList1.add(textState1);
    locationArrayList2.add(textState2);
    locationArrayList3.add(textState3);
    confirm = true
}

@Composable
fun Hotspotmap(hotspotViewModel: HotspotViewModel,
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

    fun getLocationFromAddress(strAddress: String?): GeoPoint? {
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

    AndroidView(
        factory = {
            mapView.apply {
                mapView.getMapAsync { googleMap->
                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                        if (confirm) {
                            var i = 0
                            while (i <= locationArrayList1.size-1) {
                                val locName = getLocationFromAddress(locationArrayList1[i])
                                val markerOptionsDestination = MarkerOptions()
                                    .position(
                                        com.google.android.gms.maps.model.LatLng(
                                            locName?.latitude.toString().toDouble(),
                                            locName?.longitude.toString().toDouble()
                                        )
                                    )
                                    .title(locationArrayList2[i])
                                    .snippet(locationArrayList3[i])
                                googleMap.addMarker(markerOptionsDestination)
                                i++
                            }
                        }

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