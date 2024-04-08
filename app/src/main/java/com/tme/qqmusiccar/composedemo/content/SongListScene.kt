package com.tme.qqmusiccar.composedemo.content

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tme.qqmusiccar.composedemo.R
import com.tme.qqmusiccar.composedemo.model.Song

/**
 * Create by tinguo on 2024/4/8
 * Copyright (c) 2024 TME. All rights reserved.
 */

@Composable
fun SongListPresenter(
    songs: List<Song>,
    modifier: Modifier = Modifier,
    onSongClick: (song: Song) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        state = lazyListState
    ) {
        items(songs) { song->
            SongItemCell(song = song, onSongClick = onSongClick)
        }
    }
}

@Composable
fun SongListScene(modifier: Modifier = Modifier, onSongClick: (song: Song) -> Unit) {
    val songs = (1..1000).map { i->
        Song(i * 1L, "晴天", "周杰伦", if (i % 2 == 0) R.drawable.jay_chou else R.drawable.jay_chou_1)
    }
    SongListPresenter(songs = songs, modifier = modifier) {song->
        Log.i("SongListPage", "onSongClick: $song")
        onSongClick.invoke(song)
    }
}