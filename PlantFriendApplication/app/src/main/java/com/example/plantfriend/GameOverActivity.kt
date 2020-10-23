package com.example.plantfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat

class GameOverActivity : AppCompatActivity() {
    var buttonNext: Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        buttonNext = findViewById(R.id.btnGameOver)
    }
    fun nextScreenGameOver(view: View){

    }
}