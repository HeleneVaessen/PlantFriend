package com.example.plantfriend

import android.content.Intent
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        buttonNext = findViewById(R.id.btnMainActivity2)
        applicationContext.openFileInput("PlantHappy").use { stream ->
            score = stream.bufferedReader().use {
                it.readText()
            }
        }
        scoretxt?.text= score
    }
    fun nextScreenGameOver(view: View){
        val intent = Intent(this, MainActivity::class.java).apply {
        }
        startActivity(intent)
    }
}