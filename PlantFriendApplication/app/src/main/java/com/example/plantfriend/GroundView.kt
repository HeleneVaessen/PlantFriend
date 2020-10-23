package com.example.plantfriend

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat.startActivity


class GroundView(context: Context?) : SurfaceView(context), SurfaceHolder.Callback, View.OnTouchListener{

    // ball coordinates
    var cx : Float = 10.toFloat()
    var cy : Float = 10.toFloat()
    // last position increment

    var lastGx : Float = 0.toFloat()
    var lastGy : Float = 0.toFloat()

    // graphic size of the ball

    var picHeight: Int = 0
    var picWidth : Int = 0

    var plant: Bitmap?= null
    var cloud:Bitmap?=null
    var leaves:Bitmap?=null
    var back:Bitmap?=null
    var backbutton:Rect? =null

    var cloudLocations = java.util.Collections.synchronizedList(mutableListOf<Point>())
    var score = 0
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
    var goBack = false

    init {
        holder.addCallback(this)
        setOnTouchListener(this)
        //create a thread
        thread = DrawThread(holder, this)
        jump=true
        // get references and sizes of the objects
        val display: Display = (getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size: Point = Point()
        display.getSize(size)
        Windowwidth = size.x
        Windowheight = size.y
        plant = BitmapFactory.decodeResource(resources,R.drawable.plantgame)
        leaves = BitmapFactory.decodeResource(resources, R.drawable.grass)
        cloud = BitmapFactory.decodeResource(resources, R.drawable.cloud1)
        back = BitmapFactory.decodeResource(resources, R.drawable.back)
        cloudLocations.add(Point(Windowwidth/4, Windowheight/2))
        picHeight = plant!!.height
        picWidth = plant!!.width
        backbutton = Rect(600,0,Windowwidth.toInt(), 200)

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
            var paint = Paint()
            canvas.drawRect(backbutton!!, paint)
            plant?.let { canvas.drawBitmap(it,cx,cy,null) }
            drawClouds(canvas)
            leaves?.let { canvas.drawBitmap(it,0F,Windowheight.minus(leaves!!.height).toFloat(),null) }
            back?.let { canvas.drawBitmap(it,500F,-20F,null) }
            score(canvas)
        }
    }
    override fun onTouch(view: View?, event: MotionEvent): Boolean {
        if(event.getX()> 600 && event.getX()<Windowwidth && event.getY()>0F && event.getY()<200) {
            goBack=true
            return true
        }
        else return false
    }
    fun addClouds(){
        val randomX = List(10){kotlin.random.Random.nextInt(0, Windowwidth-450)}
        val randomFromList = kotlin.random.Random.nextInt(0, 9)
        while(cloudLocations.size<4){
            var highestCloud:Int=Windowheight
            for(i in 0 until cloudLocations.size step 1){
                if(cloudLocations[i].y< highestCloud){
                    highestCloud = cloudLocations[i].y
                }
            }
            val randomY = kotlin.random.Random.nextInt(500, 900)
            cloudLocations.add(Point(randomX[randomFromList], highestCloud-randomY))
            score+=1
        }
    }

    fun score(canvas: Canvas?){
        var paint = Paint()
        paint.setColor(Color.WHITE)
        paint.textSize=200F
        canvas?.drawText(score.toString(),0F, 200F, paint)
    }
    fun deleteClouds(){
        for(i in 0 until cloudLocations.size step 1){
            if(cloudLocations[i].y>Windowheight+200)
            {
                cloudLocations.removeAt(i)
                break
            }
        }
    }
    fun moveClouds(){
        for (i in 0 until cloudLocations.size step 1){
            cloudLocations[i].y +=10

        }
    }
    fun drawClouds(canvas: Canvas?){
        deleteClouds()
        addClouds()
        for(i in 0 until cloudLocations.size step 1){
            if(i==cloudLocations.size){
                break
            }
            cloud?.let { canvas?.drawBitmap(it,cloudLocations[i].x.toFloat(),cloudLocations[i].y.toFloat(),null) }

        }
    }
    fun onCollide(){
        for(i in 0 until cloudLocations.size step 1){
            if((cx >=cloudLocations[i].x.toFloat()-180F && cx<= cloudLocations[i].x.toFloat()+ 200F)&&(cy <=cloudLocations[i].y.toFloat()-150 && cy>= cloudLocations[i].y.toFloat()-350)){
                jump = true
                jumpTimer =0
                cloudLocations.removeAt(i)
                break
            }
        }
    }

    fun jump(){
        if(jumpTimer<=100&& jump==true) {
            jumpTimer+=1
            cy-=10
            moveClouds()
        }
        else{
            jump=false
            cy+=10
            onCollide()
        }
    }

    fun gameOver(){
        if(cy>=Windowheight-500F)
        {
            gameOver=true
        }
    }



    fun updateMe(inx : Float , iny : Float){
        if(goBack==true)
        {
            val intent = Intent(context, MainActivity::class.java).apply {
            }
            startActivity(context, intent, Bundle.EMPTY)
        }
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

        jump()
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