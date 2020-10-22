package com.example.plantfriend

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat.startActivity

class GroundView(context: Context?) : SurfaceView(context), SurfaceHolder.Callback{

    // ball coordinates
    var cx : Float = 10.toFloat()
    var cy : Float = 10.toFloat()
    // last position increment

    var lastGx : Float = 0.toFloat()
    var lastGy : Float = 0.toFloat()

    // graphic size of the ball

    var picHeight: Int = 0
    var picWidth : Int = 0

    var icon: Bitmap?= null
    var cloud:Bitmap?=null
    var leaves:Bitmap?=null

    var cloudLocations = mutableListOf<Point>()
    var clouds = mutableListOf<Unit>()
    // window size

    var Windowwidth : Int = 0
    var Windowheight : Int = 0

    // is touching the edge ?

    var noBorderX = false
    var noBorderY = false

    var thread : DrawThread?= null

    var gameOver:Boolean=false
    var jump : Boolean = false
    var jumpTimer : Int = 0


    init {
        holder.addCallback(this)
        //create a thread
        thread = DrawThread(holder, this)
        jump=true
        // get references and sizes of the objects
        val display: Display = (getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size: Point = Point()
        display.getSize(size)
        Windowwidth = size.x
        Windowheight = size.y
        icon = BitmapFactory.decodeResource(resources,R.drawable.plantgame)
        leaves = BitmapFactory.decodeResource(resources, R.drawable.grass)
        cloud = BitmapFactory.decodeResource(resources, R.drawable.cloud1)
        cloudLocations.add(Point(Windowwidth/4, Windowheight/2))
        picHeight = icon!!.height
        picWidth = icon!!.width
}

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        thread!!.setRunning(true)
        thread!!.start()
    }

    override fun draw(canvas: Canvas?) {
        if (canvas != null){
            super.draw(canvas)
            canvas.drawRGB(80,192,243)
            icon?.let { canvas.drawBitmap(it,cx,cy,null) }
            leaves?.let { canvas.drawBitmap(it,0F,Windowheight.minus(leaves!!.height).toFloat(),null) }
            cloudManager(canvas)
        }
    }

    fun cloudManager(canvas: Canvas?){
        for(i in 0 until cloudLocations.size step 1){
            cloud?.let { canvas?.drawBitmap(it,cloudLocations[i].x.toFloat(),cloudLocations[i].y.toFloat(),null) }

        }
    }
    fun onCollide(){
        for(i in 0 until cloudLocations.size step 1){
            if((cx >=cloudLocations[i].x.toFloat()-180F && cx<= cloudLocations[i].x.toFloat()+ 200F)&&(cy >=cloudLocations[i].y.toFloat()-250F && cy<= cloudLocations[i].y.toFloat())){
                jump = true
                jumpTimer =0
            }
        }
    }

    fun gameOver(){
        if(cy>=Windowheight-500F)
        {
            gameOver=true
        }
    }



    fun updateMe(inx : Float , iny : Float){
        lastGx += 1- inx
        lastGy += iny
        cx += lastGx

 //       cy += lastGy
        if(cx > (Windowwidth - picWidth)){
            cx = (Windowwidth - picWidth).toFloat()
            lastGx = 0F

        }
        else if(cx < (0)){
            cx = 0F
            lastGx = 0F

        }
        else{ noBorderX = true }

        if(jumpTimer<=100&& jump==true) {
            jumpTimer+=1
            cy-=10
        }
        else{
            jump=false
            cy+=10
            onCollide()
        }
        if (cy > (Windowheight - picHeight)){
            cy = (Windowheight - picHeight).toFloat()
            lastGy = 0F

        }

        else if(cy < (0)){
            cy = 0F
            lastGy = 0F
        }
        else{ noBorderY = true }
        gameOver()
        if(gameOver==true)
        {
            val intent = Intent(context, GameActivity::class.java).apply {
            }
            startActivity(context, intent, Bundle.EMPTY)
        }
        invalidate()

    }
}