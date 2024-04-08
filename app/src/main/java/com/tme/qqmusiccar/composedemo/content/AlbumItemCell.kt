package com.tme.qqmusiccar.composedemo.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tme.qqmusiccar.composedemo.R
import com.tme.qqmusiccar.composedemo.model.Album

/**
 * Create by tinguo on 2024/4/8
 * Copyright (c) 2024 TME. All rights reserved.
 */

@Composable
fun AlbumItemCell(
    album: Album,
    modifier: Modifier = Modifier,
    onAlbumClick: ((album: Album) -> Unit)? = null
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .clickable {
                onAlbumClick?.invoke(album)
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.car_background), contentDescription = null,
                modifier = Modifier
                    .size(98.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(id = album.cover), contentDescription = null,
                modifier = Modifier.size(98.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(text = album.name, fontSize = 16.sp, color = Color(0xff000000))

        Text(text = album.artist, fontSize = 12.sp, color = Color(0xcc000000))
    }

}

@Preview
@Composable
private fun AlbumItemCellPreview() {
    val album = Album(1L, "摩羯座", "周杰伦", R.drawable.icon_login_qq)
    AlbumItemCell(album = album)
}