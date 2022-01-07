package com.example.hotspot_f2.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.example.hotspot_f2.Database
import com.example.hotspot_f2.HotspotData
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
    val hotspotrepository = Database()
    val getAlldata = hotspotrepository.getHotspots()
    LazyColumn{
        items(items = getAlldata) { hotspot ->
            HotspotList(hotspot = hotspot )
        }
    }


}
@Composable
fun HotspotList(hotspot: HotspotData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
    )
    {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = White)) {
        Image(
        painter = hotspot.image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RoundedCornerShape(20))
            .fillMaxHeight()
            .width(500.dp)
            .align(Center)
            .padding(horizontal = 2.dp,vertical = 5.dp))


    }



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 1.dp)
                .height(70.dp)
                .background(color = White)


        ) {
            Column {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                ) {

                    Column {
                        Row {
                            Text(
                                text = hotspot.title,
                                color = DarkGray,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 1.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                        }
                        Text(
                            text = hotspot.type + " - " + hotspot.locationname,
                            color = DarkGray,
                            modifier = Modifier
                                .padding(horizontal = 9.dp, vertical = 1.dp),
                            fontSize = 13.sp,
                        )
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_people_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(18.dp)
                            )
                            Text(
                                color = DarkGray,
                                modifier = Modifier
                                    .padding(horizontal = 3.dp, vertical = 1.dp),
                                fontSize = 12.sp,
                                text = "" + hotspot.checkedins + " indtjekninger lige nu "
                            )
                        }
                    }
                }
            }
        }
    }
}

    @Preview(showBackground = true)
    @Composable
    fun HotspotListScreenPreview() {
        DisplayList()

    }



