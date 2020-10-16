package com.example.plantfriend

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.io.File

class WaterDecrease : BroadcastReceiver() {
    val fileName = "PlantData"
    lateinit var context: Context
    var file: File? = null
    override fun onReceive(context:Context, intent:Intent) {
        file = File(context.filesDir, fileName)
        this.context = context
        var content: String? = null
        context.openFileInput(fileName).use { stream ->
            content = stream.bufferedReader().use {
                it.readText()
            }
        }
        if (content?.toInt()!! >= 10) {
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write((content?.toInt()!! - 10).toString().toByteArray())
            }
        }
    }
    }