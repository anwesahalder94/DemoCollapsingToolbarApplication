package com.example.demoapiapplication.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapiapplication.R
import com.example.demoapiapplication.model.Song

class SongAdapter(val songlist: ArrayList<Song>, private val context: Activity) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SongViewHolder {
        var view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_song, viewGroup, false);

        return SongViewHolder(view)
    }

    override fun getItemCount(): Int {
        return songlist.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.textSongTitle.text = songlist[position].title
        holder.textSongLength.text = songlist[position].length
    }

    inner class SongViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val textSongTitle: TextView = view.findViewById(R.id.text_song_title)
        val textSongLength: TextView = view.findViewById(R.id.text_song_length)
        val cardView: CardView = view.findViewById(R.id.card_view)
    }
}