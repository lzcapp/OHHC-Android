package top.rainysummer.openhappyhackingcalendar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)

        val c: Calendar = Calendar.getInstance()
        val monthName = arrayOf(
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"
        )
        val month = monthName[c.get(Calendar.MONTH)]
        val textView = findViewById<TextView>(R.id.textMonth)
        textView.text = month

        val startOfMonth = Calendar.getInstance()
        startOfMonth.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1)
        var weekIndex: Int = startOfMonth.get(Calendar.DAY_OF_WEEK) - 2
        if (weekIndex == -1) {
            weekIndex = 6
        }

        val daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 0 until daysInMonth) {
            val index = weekIndex + i
            val wk = index % 7
            val it = index / 7
            val id: Int =
                resources.getIdentifier("day$it$wk", "id", packageName)
            val day = findViewById<TextView>(id)
            day.text = (i + 1).toString()
        }

        val detector = GestureDetector(this, object : SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                val intent = Intent()
                intent.setClass(this@MainActivity, DayActivity::class.java)
                startActivity(intent)
                this@MainActivity.finish()
                return true
            }

            override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                return true
            }

            override fun onDown(e: MotionEvent): Boolean {
                return true
            }
        })

        val layout = findViewById<LinearLayout>(R.id.layout1)
        layout.setOnTouchListener(OnTouchListener { _, event -> detector.onTouchEvent(event) })
    }
}