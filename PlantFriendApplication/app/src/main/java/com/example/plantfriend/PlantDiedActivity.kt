package com.example.plantfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class PlantDiedActivity : AppCompatActivity() {
    var button:Button?=null
    var txtLifeSpan:TextView?=null
    var txtName:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_died)
        var dead = LocalDate.now()
        button = findViewById(R.id.btnNewPlant)
        txtLifeSpan = findViewById(R.id.txtTime)
        txtLifeSpan?.setText(dead.toString())
        var text:String = ""
        applicationContext.openFileInput("PlantName").use { stream ->
            text = stream.bufferedReader().use {
                it.readText()
            }
        }
        txtName?.setText(text)
    }

    fun newPlant(view:View){
        deleteFile("PlantName")
        deleteFile("PlantData")
        deleteFile("PlantHappy")
        val intent = Intent(this, MainActivity::class.java).apply {
        }
        startActivity(intent)
    }
}