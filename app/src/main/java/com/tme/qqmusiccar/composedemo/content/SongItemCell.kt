package com.tme.qqmusiccar.composedemo.content

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tme.qqmusiccar.composedemo.R
import com.tme.qqmusiccar.composedemo.model.Song

/**
 * Create by tinguo on 2024/4/7
 * Copyright (c) 2024 TME. All rights reserved.
 */

@Composable
fun SongItemCell(
    song: Song,
    onSongClick: ((song: Song) -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 10.dp)
            .clickable {
                onSongClick?.invoke(song)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(5.dp))

        Text(text = "${song.id}",  fontSize = 32.sp, color = Color(0xff000000),
            fontStyle = if (song.id <= 3) FontStyle.Italic else FontStyle.Normal,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.width(5.dp))


        Box {
            Image(
                painter = painterResource(id = R.drawable.car_background),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .wrapContentSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(id = song.cover),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp, 30.dp)
                    .wrapContentSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(text = song.name, fontSize = 20.sp, color = Color(0xff000000))
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = song.artist, fontSize = 16.sp, color = Color(0xf8000000))
        }
    }
}

@Preview
@Composable
private fun SongItemCellPreview() {
    val song = Song(1L, "晴天", "周杰伦", R.drawable.icon_login_qq)
    SongItemCell(song = song, onSongClick = { item->
        Log.i("SongItemCell", "click $item")
    })
}