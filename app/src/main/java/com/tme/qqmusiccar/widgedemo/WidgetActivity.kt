package com.tme.qqmusiccar.widgedemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.tme.qqmusiccar.composedemo.R
import com.tme.qqmusiccar.composedemo.toolkit.FpsTrace

/**
 * Create by tinguo on 2024/4/8
 * Copyright (c) 2024 TME. All rights reserved.
 */
class WidgetActivity: Activity() {

    private val fpsTrace = FpsTrace()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fpsTrace.setup(this)

        setContentView(R.layout.activity_widget)

        findViewById<Button>(R.id.jump_song_list)?.setOnClickListener {
            val intent = Intent(this, SongListActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.jump_album_list)?.setOnClickListener {
            val intent = Intent(this, AlbumListActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.jump_player)?.setOnClickListener {

        }
    }

}