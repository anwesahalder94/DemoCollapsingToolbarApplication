package com.example.demoapiapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demoapiapplication.adapter.SongAdapter
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_song.*
import com.example.demoapiapplication.model.Album
import com.example.demoapiapplication.model.Song
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_song.app_bar_image
import kotlinx.android.synthetic.main.activity_song.appbar
import kotlinx.android.synthetic.main.activity_song.toolbar


class SongActivity : AppCompatActivity() {

    lateinit var songAdapter: SongAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)
        setSupportActionBar(toolbar)

        init()
        setEventListener()
    }

    private fun init() {
        var album = intent.getParcelableExtra<Album>("Album")
        var songsList = intent.getParcelableArrayListExtra<Song>("Songs")
        Picasso.get().load(album?.image).into(app_bar_image)
        collapsing_toolbar.title = album.title.toString()
        text_desc.text = album.description.toString()

        layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_song_list?.setHasFixedSize(true)
        rv_song_list?.layoutManager = layoutManager
        songAdapter = SongAdapter(songsList!!, this)
        rv_song_list.adapter = songAdapter
    }

    private fun setEventListener() {
        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout!!.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                } else if (isShow) {
                    isShow = false
                }

            }

        })
    }
}
