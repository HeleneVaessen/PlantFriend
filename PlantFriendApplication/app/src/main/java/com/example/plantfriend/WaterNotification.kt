package com.example.plantfriend

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.io.File


class WaterNotification : BroadcastReceiver() {
lateinit var nameFile:File
    val namefileName:String = "PlantName"
lateinit var pendingIntent:PendingIntent
    override fun onReceive(context:Context, intent:Intent) {
        pendingIntent= Intent(context, MainActivity::class.java).let { intent ->
            PendingIntent.getActivity(context, 0, intent, 0)

        }
        nameFile = File(context.filesDir, namefileName)
        if(nameFile.exists()) {
            var name: String? = null
            context.openFileInput(namefileName).use { stream ->
                name = stream.bufferedReader().use {
                    it.readText()
                }
            }
        var builder = NotificationCompat.Builder(context!!, "Water")
            .setSmallIcon(R.drawable.plantthirsty)
            .setContentTitle("PlantFriend")
            .setContentText(name +" is thirsty")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        var notification = NotificationManagerCompat.from(context)
        notification.notify(0, builder.build())
    }
    }
}


