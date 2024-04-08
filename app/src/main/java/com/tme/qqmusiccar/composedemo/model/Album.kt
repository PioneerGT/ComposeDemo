package com.tme.qqmusiccar.composedemo.model

import androidx.annotation.DrawableRes

/**
 * Create by tinguo on 2024/4/8
 * Copyright (c) 2024 TME. All rights reserved.
 */
data class Album(
    val id: Long,
    val name: String,
    val artist: String,
    @DrawableRes val cover: Int
)