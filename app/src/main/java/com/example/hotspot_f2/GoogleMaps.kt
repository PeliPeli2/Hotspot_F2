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

@Composable
fun Hotspotmap(
    modifier:Modifier=Modifier,
    OnReady:(GoogleMap)->Unit

)
{
    val context= LocalContext.current
    val markerOptions = MarkerOptions()
        .position(LatLng(55.73,12.3962))
        .title("Brønnum")
        .snippet("Adjacent to the buzzing Kongens Nytorv square is Brønnum, a hushed," +
                " historic retreat—the perfect post-sightseeing reprieve. Specializing in Champagne," +
                " along with a well-edited list of wine and cocktails and late-afternoon live jazz performances.")

    val markerOptions2 = MarkerOptions()
        .position(LatLng(55.73,12.39))
        .title("Duck And Cover")
        .snippet("Duck and Cover is a subterranean sanctuary in bustling Vesterbro. " +
                "In contrast to the neighborhood’s other packed-to-the-gills bars, it's a relaxed, " +
                "ideal place to pop in for a subdued nightcap amid a striking collection of vintage furniture and lighting.")

    val markerOptions3 = MarkerOptions()
        .position(LatLng(55.7250,12.3910))
        .title("Ørsted")
        .snippet("Copenhagen's craft beer scene is impressive, and one of the best places to become acquainted with it is Ørsted Ølbar," +
                " located a stone’s throw from Nørreport Station and tranquil Ørstedparken.")

    val markerOptions4 = MarkerOptions()
        .position(LatLng(55.7279,12.39))
        .title("K-bar")
        .snippet("K-bar is named for Kirsten Holm, " +
                "a pioneer of the Copenhagen cocktail scene. Years after opening, this bar, " +
                "situated near Højbro Square, remains a sought-after drinks destination")



    val cameraPosition = CameraPosition.builder()
        .target(LatLng(55.7314, 12.3962))
        .zoom(15f)
        .build()

    val mapView= remember {
        MapView(context)

    }

    val lifecycle= LocalLifecycleOwner.current.lifecycle

    lifecycle.addObserver(rememberMapLifecycle(mapView ))

    AndroidView(
        factory = {
                  mapView.apply {
                      mapView.getMapAsync { googleMap->
                          googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                          googleMap.addMarker(markerOptions)
                          googleMap.addMarker(markerOptions2)
                          googleMap.addMarker(markerOptions3)
                          googleMap.addMarker(markerOptions4)
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