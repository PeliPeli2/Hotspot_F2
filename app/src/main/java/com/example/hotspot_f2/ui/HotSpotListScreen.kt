package com.example.hotspot_f2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotspot_f2.R

@Composable

fun HotSpotListScreen(name: String,type: String, checkins: Int, image:Painter) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HotspotList(name, type, checkins, image)
    }
}

@Composable
fun HotspotList(name: String,type: String, checkins: Int, image:Painter) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(80.dp)
            .background(color = Blue)

    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .align(Center)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .border(
                        width = 1.dp,
                        color = White,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .size(60.dp)
                    .align(CenterVertically)



            )
Column {
    Text(
        text = name + " - " + type,
        color = White,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    Text(
        color = White,
        modifier = Modifier
            .padding(horizontal = 16.dp,vertical = 10.dp),
        fontSize = 13.sp,
        text = "Indtjekninger: $checkins"
    )
    Text(color = White,
        modifier = Modifier
            .padding(horizontal = 16.dp,vertical = 5.dp),
        fontSize = 13.sp,
        text = "København K")
}
        }
    }
}

    @Preview(showBackground = true)
    @Composable
    fun HotspotListScreenPreview() {
        val first: Array<Array<String>> = arrayOf(
            arrayOf("2", "4", "6"),
            arrayOf("1", "2", "5"),
            arrayOf("1", "2", "5")
        )

        HotSpotListScreen("Kassen", "Bar", 2, painterResource(id = R.drawable.bar))
    }



