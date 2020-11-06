package com.example.plantfriend

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
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
    val mediaplayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_died)
        var dead = LocalDate.now()
        button = findViewById(R.id.btnNewPlant)
        txtLifeSpan = findViewById(R.id.txtTime)
        txtLifeSpan?.setText(dead.toString())
        var text:String = ""
        mediaplayer.reset()
        val afd: AssetFileDescriptor = this.assets!!.openFd("dead.mp3")
        mediaplayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mediaplayer.prepare()
        mediaplayer.isLooping=true
        mediaplayer.start()
        applicationContext.openFileInput("PlantName").use { stream ->
            text = stream.bufferedReader().use {
                it.readText()
            }
        }
        txtName?.setText(text)
    }

    override fun onStop() {
        mediaplayer.stop()
        super.onStop()
    }

    override fun onPause() {
        mediaplayer.stop()
        super.onPause()
    }

    override fun onRestart() {
        mediaplayer.reset()
        val afd: AssetFileDescriptor = this.assets!!.openFd("dead.mp3")
        mediaplayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mediaplayer.prepare()
        mediaplayer.isLooping=true
        mediaplayer.start()
        super.onRestart()
    }


    fun newPlant(view:View){
        applicationContext.openFileOutput("PlantName", Context.MODE_PRIVATE).use {
            it.write("".toByteArray())
        }
            applicationContext.openFileOutput("PlantData", Context.MODE_PRIVATE).use {
                it.write("70".toByteArray())
            }
                applicationContext.openFileOutput("PlantHappy", Context.MODE_PRIVATE).use {
                    it.write("70".toByteArray())
                }
                    val intent = Intent(this, MainActivity::class.java).apply {
                    }
        mediaplayer.stop()
                    startActivity(intent)
                }
            }
