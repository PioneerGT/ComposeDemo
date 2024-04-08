package com.tme.qqmusiccar.composedemo.model

import androidx.annotation.DrawableRes

/**
 * Create by tinguo on 2024/4/7
 * Copyright (c) 2024 TME. All rights reserved.
 */
data class Song(
    val id: Long,
    val name: String, // CharSequence 与Android Widget不同
    val artist: String,
    @DrawableRes val cover: Int
)
