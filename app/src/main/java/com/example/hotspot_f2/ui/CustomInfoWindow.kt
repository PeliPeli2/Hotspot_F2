package com.example.hotspot_f2.ui

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.hotspot_f2.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker


class CustomInfoWindow (context: Context) : GoogleMap.InfoWindowAdapter {

    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.custom_info_window, null)

    private fun renderWindowText(marker: Marker, view: View){

        val Markertitle = view.findViewById<TextView>(R.id.title)
        val Markersnippet = view.findViewById<TextView>(R.id.snippet)

        Markertitle.text = marker.title
        Markersnippet.text = marker.snippet

        when(marker.title) {
            "Brønnum" -> view.findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.broennum)
            "Duck And Cover" -> view.findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.duck_and_cover)
            "Ørsted" -> view.findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.oersted)
            "K-bar" -> view.findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.k_bar)
            else -> view.findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.broennum)
        }
        view.findViewById<ImageView>(R.id.imageView2).visibility = View.VISIBLE
        view.findViewById<Button>(R.id.button).setText("Check in")
    }

    override fun getInfoContents(marker: Marker): View? {
        renderWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        renderWindowText(marker, mWindow)
        return mWindow
    }

}