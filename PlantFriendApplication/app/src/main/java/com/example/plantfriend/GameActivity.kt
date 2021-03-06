package com.example.plantfriend

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.AssetFileDescriptor
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    var plant:ImageView?=null
    var ground:GroundView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        plant = findViewById(R.id.planthappy)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val size = Point()
        val display = windowManager.defaultDisplay
        display.getSize(size)
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        ground= GroundView(this)
        setContentView(ground)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }


    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onRestart() {
            ground!!.backgroundplayer.reset()
            val afd: AssetFileDescriptor = this.assets!!.openFd("game.mp3")
            ground!!.backgroundplayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            ground!!.backgroundplayer.prepare()
            ground!!.backgroundplayer.isLooping=true
            ground!!.backgroundplayer.start()
        super.onRestart()
    }

    override fun onStop() {
        ground!!.backgroundplayer.stop()
        sensorManager.unregisterListener(this)
        super.onStop()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
    override fun onPause() {
        ground!!.backgroundplayer.stop()
        super.onPause()
        sensorManager.unregisterListener(this)
    }
    override fun onSensorChanged(sensorEvent: SensorEvent){
        if(sensorEvent!=null)
        {
            ground!!.updateMe(sensorEvent.values[0], sensorEvent.values[1])
        }
    }
}