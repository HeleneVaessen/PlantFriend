package com.example.plantfriend

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class WaterNotification : BroadcastReceiver() {
    override fun onReceive(context:Context, intent:Intent) {
        var intentType = intent.extras?.get("Type")
        if (intentType == "WaterNotification") {
            waterNotification(context)
        }
        if(intentType == "WaterDecrease")
        {
            waterDecrease()
        }
    }
    fun waterNotification(context: Context){
        var builder = NotificationCompat.Builder(context!!, "Water")
            .setSmallIcon(R.drawable.plantthirsty)
            .setContentTitle("PlantFriend")
            .setContentText("Your plant is thirsty")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        var notification = NotificationManagerCompat.from(context)
        notification.notify(0, builder.build())
    }
    fun waterDecrease()
    {
        MainActivity().timeWaterDecrease()
    }
    }
