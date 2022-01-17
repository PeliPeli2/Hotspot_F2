package com.example.hotspot_f2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.example.hotspot_f2.nav.NavigationItem
import com.example.hotspot_f2.ui.CustomInfoWindow
import com.example.hotspot_f2.HotSpotActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions




fun testAddingAHotspot(textState1: String, textState2: String, textState3: String, textState4: String)
{
    /*
    val newMarker = MarkerOptions()
        .position(LatLng(textState1.toDouble(), textState2.toDouble()))
        .title(textState3)
        .snippet(textState4)
    */
}


@Composable
fun Hotspotmap(hotspotViewModel: HotspotViewModel, navController: NavHostController,
    modifier:Modifier=Modifier,
    OnReady:(GoogleMap)->Unit
)
{
    val context= LocalContext.current
    val markers = mutableListOf<MarkerOptions>()
    hotspotViewModel.hotspots.forEach {
        markers.add(
            MarkerOptions()
                .position(LatLng(it.location.latitude, it.location.longitude))
                .title(it.title)
                .snippet(it.description)
        )
    }

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
                          markers.forEach { googleMap.addMarker(it) }
                          googleMap.setInfoWindowAdapter(CustomInfoWindow(this.context))
                          googleMap.setOnInfoWindowClickListener { markerOptions ->
                              val intent = Intent(context, HotSpotActivity::class.java)
                              intent.putExtra("hotspotname", markerOptions.title.toString())
                              intent.putExtra("hotspotinfo", markerOptions.snippet.toString())
                              //random checkin value should be changed
                              intent.putExtra("hotspotcheckins",10)
                              //If statements should be changed
                                  if (markerOptions.title == "Brønnum") {
                                      intent.putExtra("hotspotimage", R.drawable.broennum)
                                  }
                                  if (markerOptions.title == "Duck And Cover") {
                                      intent.putExtra("hotspotimage", R.drawable.duck_and_cover)
                                  }
                                  if (markerOptions.title == "Ørsted") {
                                      intent.putExtra("hotspotimage", R.drawable.oersted)
                                  }
                                  if (markerOptions.title == "K-bar") {
                                      intent.putExtra("hotspotimage", R.drawable.k_bar)
                                  }
                              context.startActivity(intent)
                              hotspotViewModel.title = markerOptions.title.toString()
                              Log.d("marker", markerOptions.title.toString())
                              Log.d("hotspot", hotspotViewModel.title)
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