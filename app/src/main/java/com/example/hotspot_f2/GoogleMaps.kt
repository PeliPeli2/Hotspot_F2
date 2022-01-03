package com.example.hotspot_f2

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import java.lang.IllegalStateException
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition





@Composable
fun Hotspotmap(
    modifier:Modifier=Modifier,
    OnReady:(GoogleMap)->Unit

)
{
    val context= LocalContext.current

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