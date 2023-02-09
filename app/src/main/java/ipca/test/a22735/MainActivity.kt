package ipca.test.a22735

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val trafficControl = findViewById<TrafficControl>(R.id.trafficControl)
        val button = findViewById<Button>(R.id.start)

        button.setOnClickListener() {
            trafficControl.onButtonClick()
            button.isEnabled = false
            val handler = Handler()
            handler.postDelayed({
                button.isEnabled = true
            }, 70000)
        }
    }
}