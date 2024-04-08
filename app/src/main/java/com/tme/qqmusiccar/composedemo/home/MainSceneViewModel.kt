package com.tme.qqmusiccar.composedemo.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tme.qqmusiccar.composedemo.R
import com.tme.qqmusiccar.composedemo.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Create by tinguo on 2024/4/3
 * Copyright (c) 2024 TME. All rights reserved.
 */
class MainSceneViewModel: ViewModel() {

    private val mShowSplash: MutableState<Boolean> = mutableStateOf(true)
    val showSplash: State<Boolean> = mShowSplash

    fun splashFinished() {
        mShowSplash.value = false
    }

    private val mCurSong = MutableStateFlow(Song(0L, "", "", R.drawable.icon_login_qq))
    val curSong: StateFlow<Song> = mCurSong.asStateFlow()

    fun switchSong(song: Song) {
        mCurSong.update { song }
    }

}