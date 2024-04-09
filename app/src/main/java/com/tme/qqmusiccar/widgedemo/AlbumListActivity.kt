package com.tme.qqmusiccar.widgedemo

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tme.qqmusiccar.composedemo.R
import com.tme.qqmusiccar.composedemo.model.Album
import com.tme.qqmusiccar.composedemo.toolkit.FpsTrace

/**
 * Create by tinguo on 2024/4/8
 * Copyright (c) 2024 TME. All rights reserved.
 */
class AlbumListActivity: Activity() {

    val fpsTrace = FpsTrace()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fpsTrace.setup(this)
        setContentView(R.layout.activity_songlist)

        val albums = (1..1000).map {
            Album(it * 1L, "魔杰座", "周杰伦", if (it % 2 == 0) R.drawable.jay_chou else R.drawable.jay_chou_1)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = AlbumListAdapter(albums)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
    }

    private class AlbumListAdapter(val albums: List<Album>) : RecyclerView.Adapter<AlbumItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemViewHolder {
            return AlbumItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
            )
        }

        override fun getItemCount(): Int = albums.size

        override fun onBindViewHolder(holder: AlbumItemViewHolder, position: Int) {
            albums.getOrNull(position)?.let {
                holder.setData(it)
            }
        }
    }

    private class AlbumItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView.findViewById(R.id.album_img)
        private val name: TextView = itemView.findViewById(R.id.album_name)
        private val artist: TextView = itemView.findViewById(R.id.album_artist)

        fun setData(album: Album) {
            Glide.with(img)
                .load(album.cover)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(img)
            name.text = album.name
            artist.text = album.artist
        }
    }
}