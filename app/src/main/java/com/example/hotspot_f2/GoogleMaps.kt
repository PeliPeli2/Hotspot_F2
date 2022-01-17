package com.example.hotspot_f2

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
import java.util.*
//val destination1 = LatLng(55.7215, 12.3639)

val locationArrayList1: MutableList<String> = ArrayList()
val locationArrayList2: MutableList<String> = ArrayList()
val locationArrayList3: MutableList<String> = ArrayList()
val locationArrayList4: MutableList<String> = ArrayList()
var confirm: Boolean = false
fun testAddingAHotspot(textState1: String, textState2: String, textState3: String, textState4: String)
{
    locationArrayList1.add(textState1);
    locationArrayList2.add(textState2);
    locationArrayList3.add(textState3);
    locationArrayList4.add(textState4);
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

    AndroidView(
        factory = {
            mapView.apply {
                mapView.getMapAsync { googleMap->
                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                        if (confirm) {
                            var i = 0
                            while (i <= locationArrayList1.size-1) {
                                val markerOptionsDestination = MarkerOptions()
                                    .position(
                                        com.google.android.gms.maps.model.LatLng(
                                            locationArrayList1[i].toDouble(),
                                            locationArrayList2[i].toDouble()
                                        )
                                    )
                                    .title(locationArrayList3[i])
                                    .snippet(locationArrayList4[i])
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