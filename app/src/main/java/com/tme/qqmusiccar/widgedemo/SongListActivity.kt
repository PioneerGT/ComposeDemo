package com.tme.qqmusiccar.widgedemo

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.tme.qqmusiccar.composedemo.R
import com.tme.qqmusiccar.composedemo.model.Song
import com.tme.qqmusiccar.composedemo.toolkit.FpsTrace

/**
 * Create by tinguo on 2024/4/8
 * Copyright (c) 2024 TME. All rights reserved.
 */
class SongListActivity: Activity() {

    val fpsTrace = FpsTrace()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fpsTrace.setup(this)
        setContentView(R.layout.activity_songlist)

        val songs = (1..1000).map {
            Song(it * 1L, "晴天", "周杰伦", if (it % 2 == 0) R.drawable.jay_chou else R.drawable.jay_chou_1)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = SongListAdapter(songs)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private class SongListAdapter(val songs: List<Song>): RecyclerView.Adapter<SongItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {
            return SongItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
            )
        }

        override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
            songs.getOrNull(position)?.let {
                holder.setData(it)
            }
        }

        override fun getItemCount(): Int  = songs.size
    }

    private class SongItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val pos: TextView = itemView.findViewById(R.id.song_pos)
        private val img: ImageView = itemView.findViewById(R.id.song_img)
        private val name: TextView = itemView.findViewById(R.id.song_name)
        private val artist: TextView = itemView.findViewById(R.id.song_artist)

        fun setData(song: Song) {
            pos.text = "${song.id}"
            img.setImageResource(song.cover)
            name.text = song.name
            artist.text = song.artist
        }
    }

}