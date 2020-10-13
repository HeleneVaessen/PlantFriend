package com.example.plantfriend

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.io.File


class WaterNotification : BroadcastReceiver() {

    override fun onReceive(context:Context, intent:Intent) {
        var builder = NotificationCompat.Builder(context!!, "Water")
            .setSmallIcon(R.drawable.plantthirsty)
            .setContentTitle("PlantFriend")
            .setContentText("Your plant is thirsty")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        var notification = NotificationManagerCompat.from(context)
        notification.notify(0, builder.build())
    }
    }
