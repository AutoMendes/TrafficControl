package ipca.test.a22735

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import com.google.android.gms.maps.model.Circle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.NonCancellable.start
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class TrafficControl : View {

    var active = 0

    private var onValueChange : ((Int)->Unit)? = null

    fun setOnValueChangeListener (callback :(Int)->Unit ) {
        onValueChange = callback
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        //create a rectangle that surrounds the traffic light
        val rect = Rect(width/2-250,height/2-700,width/2+250,height/2+700)
        canvas?.drawRect(rect, paint)

        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        canvas?.drawCircle((width / 2).toFloat(), (height / 2 - 500).toFloat(), 150F, paint)

        paint.color = Color.YELLOW
        paint.style = Paint.Style.STROKE
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 150F, paint)

        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        canvas?.drawCircle((width / 2).toFloat(), (height / 2 + 500).toFloat(), 150F, paint)

        if (active == 1) {
            paint.color = Color.RED
            paint.style = Paint.Style.STROKE
            canvas?.drawCircle((width / 2).toFloat(), (height / 2 - 500).toFloat(), 150F, paint)

            paint.style = Paint.Style.FILL
            paint.color = Color.YELLOW
            canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 150F, paint)

        } else if (active == 2) {
            paint.color = Color.YELLOW
            paint.style = Paint.Style.STROKE
            canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 150F, paint)

            paint.style = Paint.Style.FILL
            paint.color = Color.RED
            canvas?.drawCircle((width / 2).toFloat(), (height / 2 - 500).toFloat(), 150F, paint)
        } else {
            paint.color = Color.RED
            paint.style = Paint.Style.STROKE
            canvas?.drawCircle((width / 2).toFloat(), (height / 2 - 500).toFloat(), 150F, paint)

            paint.color = Color.GREEN
            paint.style = Paint.Style.FILL
            canvas?.drawCircle((width / 2).toFloat(), (height / 2 + 500).toFloat(), 150F, paint)
        }
    }

    fun onButtonClick() {
        active = 1
        onValueChange?.invoke(active)
        invalidate()

        // Wait 5 seconds before changing to yellow
        val handler = Handler()
        handler.postDelayed({
            active = 2
            onValueChange?.invoke(active)
            invalidate()

            // Wait 5 seconds before changing to red
            handler.postDelayed({
                active = 3
                onValueChange?.invoke(active)
                invalidate()
                //Wait 1 minute before changing to green
                handler.postDelayed({
                    active = 0
                    onValueChange?.invoke(active)
                    invalidate()
                }, 5000)
            }, 60000)
        }, 5000)
    }
}


