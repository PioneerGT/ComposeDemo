package com.tme.qqmusiccar.composedemo.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Create by tinguo on 2024/4/2
 * Copyright (c) 2024 TME. All rights reserved.
 */

private val LightColorScheme = lightColorScheme()
private val DarkColorScheme = darkColorScheme()

@Composable
fun ComposeDemoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit)
{
    val colorScheme = if (!darkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}