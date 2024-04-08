package com.tme.qqmusiccar.composedemo.content

import android.graphics.BitmapFactory
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.renderscript.Toolkit
import com.tme.qqmusiccar.composedemo.R
import com.tme.qqmusiccar.composedemo.home.MainSceneViewModel
import com.tme.qqmusiccar.composedemo.model.Song
import kotlinx.coroutines.delay

/**
 * Create by tinguo on 2024/4/8
 * Copyright (c) 2024 TME. All rights reserved.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerContent(
    song: Song,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Crossfade(targetState = song.cover, animationSpec = tween(1000), label = "") { cover->
            val bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, cover)
            bitmap?.let {
                Toolkit.blur(it, radius = 20)
            }?.asImageBitmap()?.let {
                Image(bitmap = it, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = song.cover), contentDescription = null, modifier = Modifier
                .size(128.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                ), contentScale = ContentScale.Crop)

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = "${song.id} - ${song.name}", fontSize = 38.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier
                .sizeIn(minWidth = 10.dp, maxWidth = 200.dp)
                .basicMarquee(), maxLines = 1)

            Text(text = "${song.artist}", fontSize = 20.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Normal, color = Color.LightGray, modifier = Modifier
                .sizeIn(minWidth = 10.dp, maxWidth = 120.dp)
                .basicMarquee(), maxLines = 1)
        }
    }
}

@Composable
fun PlayerScene(
    mainSceneViewModel: MainSceneViewModel,
    modifier: Modifier = Modifier
) {
    val songState = mainSceneViewModel.curSong.collectAsState()
    LaunchedEffect(key1 = Unit) {
        repeat(Int.MAX_VALUE) {
            delay(3000L)
            val nextSong = songState.value.let { it.copy(id = it.id+1, cover = (if (it.id % 2 == 0L) R.drawable.jay_chou_1 else R.drawable.jay_chou)) }
            mainSceneViewModel.switchSong(nextSong)
        }
    }
    PlayerContent(song = songState.value, modifier = modifier)
}