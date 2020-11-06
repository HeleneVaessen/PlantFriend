package com.example.plantfriend

import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat

class GameWonActivity : AppCompatActivity() {
    var button:Button?=null
    val mediaplayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_won)
        mediaplayer.reset()
        val afd: AssetFileDescriptor = this.assets!!.openFd("succes.mp3")
        mediaplayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mediaplayer.prepare()
        mediaplayer.isLooping=true
        mediaplayer.start()
        button = findViewById(R.id.btnMainActivity)
    }

    fun nextScreen(view: View){
        val intent = Intent(this, MainActivity::class.java).apply {
        }
        mediaplayer.stop()
        ContextCompat.startActivity(this, intent, Bundle.EMPTY)
    }
}