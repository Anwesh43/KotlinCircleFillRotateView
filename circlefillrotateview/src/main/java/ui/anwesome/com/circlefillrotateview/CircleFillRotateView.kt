package ui.anwesome.com.circlefillrotateview

/**
 * Created by anweshmishra on 26/02/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
class CircleFillRotateView(ctx : Context): View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class State(var dir : Float = 0f, var jDir : Int = 1, var j : Int = 0, var prevScale : Float = 0f) {
        val scales : Array<Float> = arrayOf(0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if(Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += jDir
                if(j == scales.size || j == -1) {
                    jDir *= -1
                    j += jDir
                    prevScale = scales[j] + dir
                    dir = 0f
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if(dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }
    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if(animated) {
                try {
                    updatecb()
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class CircleFill(var x : Float, var y : Float, var r : Float) {
        val state = State()
        fun draw(canvas : Canvas, paint : Paint) {
            canvas.save()
            canvas.translate(x, y)
            canvas.rotate(180f * state.scales[0])
            val path = Path()
            path.addCircle(0f, 0f, r, Path.Direction.CW)
            paint.color = Color.parseColor("#9E9E9E")
            canvas.clipPath(path)
            canvas.drawPath(path, paint)
            paint.color = Color.parseColor("#283593")
            canvas.drawRect(RectF(-r,  - r * this.state.scales[1], r, r - r * this.state.scales[1]), paint)
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
}