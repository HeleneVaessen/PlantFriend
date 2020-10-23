package com.example.plantfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat

class GameWonActivity : AppCompatActivity() {
    var button:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_won)
        button = findViewById(R.id.btnMainActivity)
    }

    fun nextScreen(view: View){
        val intent = Intent(this, MainActivity::class.java).apply {
        }
        ContextCompat.startActivity(this, intent, Bundle.EMPTY)
    }
}