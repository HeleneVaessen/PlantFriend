package com.example.plantfriend

import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder

class DrawThread (surfaceHolder: SurfaceHolder, panel : GroundView) : Thread() {
    private var surfaceHolder :SurfaceHolder ?= null
    private var panel : GroundView ?= null
    private var run = false

    init {
        this.surfaceHolder = surfaceHolder
        this.panel = panel
    }

    fun setRunning(run : Boolean){
        this.run = run
    }

    override fun run() {
        var c: Canvas?= null
        while (run){
            c = null
            try {
                c = surfaceHolder!!.lockCanvas(null)
                synchronized(surfaceHolder!!){
                    panel!!.draw(c)
                }
            }finally {
                if (c!= null){
                    surfaceHolder!!.unlockCanvasAndPost(c)
                }
            }
        }
    }

}

