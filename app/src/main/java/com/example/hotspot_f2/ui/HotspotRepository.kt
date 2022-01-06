package com.example.hotspot_f2.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.hotspot_f2.R

class HotspotRepository {
    @Composable
    fun getAllData(): List<Hotspots2>{
        return listOf(
            Hotspots2(
                "Kassen",
                "Bar",
                5,
                painterResource(id = R.drawable.bar),
            "København K"),
                Hotspots2(
                    "Pubben",
                    "Bar",
                    10,
                    painterResource(id = R.drawable.bar),
                    "Amager")

            )


    }
}