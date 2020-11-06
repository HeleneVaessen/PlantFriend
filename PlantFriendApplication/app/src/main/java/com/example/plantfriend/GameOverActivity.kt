package com.example.plantfriend

import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class GameOverActivity : AppCompatActivity() {
    var buttonNext: Button?=null
    var scoretxt: TextView?=null
    var score:String?=null
    val mediaplayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        buttonNext = findViewById(R.id.btnMainActivity2)
        scoretxt = findViewById(R.id.score)
        mediaplayer.reset()
        val afd: AssetFileDescriptor = this.assets!!.openFd("fail.mp3")
        mediaplayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mediaplayer.prepare()
        mediaplayer.isLooping=true
        mediaplayer.start()
        applicationContext.openFileInput("PlantHappy").use { stream ->
            score = stream.bufferedReader().use {
                it.readText()
            }
        }
        scoretxt?.text= (score!!.toInt().div(10)).toString()+"/20"
    }
    fun nextScreenGameOver(view: View){
        val intent = Intent(this, MainActivity::class.java).apply {
        }
        mediaplayer.stop()
        startActivity(intent)
    }
}