package com.example.hotspot_f2.ui

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hotspot_f2.Hotspot
import com.example.hotspot_f2.HotspotViewModel
import com.example.hotspot_f2.R

@Composable
fun DisplayList(hotspotViewModel: HotspotViewModel){
    LazyColumn{
        items(items = hotspotViewModel.hotspots) { hotspot ->
            HotspotList(hotspot = hotspot)
        }
    }
}
@Composable
fun HotspotList(hotspot: Hotspot) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = { Toast.makeText(context, hotspot.title, Toast.LENGTH_SHORT).show() })
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Image(
                painter = painterResource(id = hotspot.imageID),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .fillMaxHeight()
                    .width(500.dp)
                    .align(Center)
                    .padding(horizontal = 2.dp, vertical = 5.dp)
            )


        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
        )
        {
            Column {
                Row {
                    Text(
                        text = hotspot.title,
                        modifier = Modifier
                            .padding(vertical = 5.dp,horizontal = 5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
                Text(
                    text = hotspot.type,
                    modifier = Modifier
                        .padding(horizontal = 6.dp),
                    fontSize = 14.sp,
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 7.dp, horizontal = 6.dp)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_people_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        fontSize = 13.sp,
                        text = "${hotspot.checkins.value} indtjekninger lige nu "

                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(DarkGray)
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}



    @Preview(showBackground = true)
    @Composable
    fun HotspotListScreenPreview() {
        DisplayList(HotspotViewModel())

    }



