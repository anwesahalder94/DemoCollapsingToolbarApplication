package com.example.demoapiapplication.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapiapplication.model.Album
import com.squareup.picasso.Picasso
import com.example.demoapiapplication.R
import com.example.demoapiapplication.SongActivity
import com.example.demoapiapplication.model.Song
import kotlin.collections.ArrayList


class AlbumAdapter(val albumlist: List<Album>, private val context: Activity, val navController: NavController):
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>()
{
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): AlbumViewHolder {
        var view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_albums, viewGroup, false);

        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  albumlist.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        var eachAlbum: Album = albumlist[position]
        var songsList: ArrayList<Song> = eachAlbum.songs as ArrayList<Song>
        holder.text.text = albumlist[position].title
        Picasso.get().load(albumlist[position].image).into(holder.image)
        holder.constLayout.setOnClickListener {

            var intent = Intent(context, SongActivity::class.java)
            intent.putExtra("Album",eachAlbum )
            intent.putParcelableArrayListExtra("Songs", songsList)
            context.startActivity(intent)
        }
    }

    inner class AlbumViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        val text: TextView = view.findViewById(R.id.text_title)
        val image: ImageView = view.findViewById(R.id.image)
        val constLayout : ConstraintLayout = view.findViewById(R.id.const_layout)
    }
}