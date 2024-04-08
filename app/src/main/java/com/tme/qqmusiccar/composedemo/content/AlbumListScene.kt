package com.tme.qqmusiccar.composedemo.content

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tme.qqmusiccar.composedemo.R
import com.tme.qqmusiccar.composedemo.model.Album

/**
 * Create by tinguo on 2024/4/8
 * Copyright (c) 2024 TME. All rights reserved.
 */

@Composable
fun AlbumListPresenter(albums: List<Album>, modifier: Modifier = Modifier, onAlbumClick: (album: Album) -> Unit) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 98.dp),
        modifier = modifier,
        state = gridState,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(albums) { album->
            AlbumItemCell(album = album, onAlbumClick = {
                Log.i("AlbumListScene", "onAlbumClick $it")
                onAlbumClick.invoke(it)
            })
        }
    }
}


@Composable
fun AlbumListScene(modifier: Modifier = Modifier, onAlbumClick: (album: Album) -> Unit) {
    val albums = (1..1000).map { i->
        Album(i * 1L, "我是如此相信", "周杰伦", if (i % 2 == 0) R.drawable.jay_chou else R.drawable.jay_chou_1)
    }
    AlbumListPresenter(albums = albums, modifier = modifier, onAlbumClick = onAlbumClick)
}