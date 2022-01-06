package com.example.hotspot_f2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotspot_f2.R


/*fun HotSpotListScreen(name: String,type: String, checkins: Int, image:Painter) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HotspotList(name, type, checkins, image)
    }*/

@Composable
fun DisplayList(){
    val hotspotrepository = HotspotRepository()
    val getAlldata = hotspotrepository.getAllData()
    LazyColumn{
        items(items = getAlldata) { hotspot ->
            HotspotList(hotspot = hotspot )
        }
    }


}
@Composable
fun HotspotList(hotspot: Hotspots2) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical= 1.dp )
            .height(80.dp)
            .background(color = LightGray)

    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .align(Center)
        ) {
            Image(
                painter = hotspot.image,
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
                    text = hotspot.name + " - " + hotspot.type,
                    color = DarkGray,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    color = DarkGray,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    fontSize = 13.sp,
                    text = "Indtjekninger: " + hotspot.checkins  + " - " + hotspot.location
                )
            }
        }
    }
}

    @Preview(showBackground = true)
    @Composable
    fun HotspotListScreenPreview() {
        DisplayList()

    }



