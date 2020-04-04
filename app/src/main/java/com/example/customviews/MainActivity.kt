package com.example.customviews

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.example.customviews.customviews_ch1.OwnCustomView
import com.example.customviews.customviews_ch2.MeasuredCustomView
import com.example.customviews.utils.toPx
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        for (i in 0 until 50) {
//            val customView = MeasuredCustomView.Builder(applicationContext)
//                .topLeftColor(colors[Random.nextInt(0, 4)])
//                .topRightColor(colors[Random.nextInt(0, 4)])
//                .bottomLeftColor(colors[Random.nextInt(0, 4)])
//                .bottomRightColor(colors[Random.nextInt(0, 4)])
//                .build()
//                .apply {
//                val w = Random.nextInt(200) + 50
//                val h = Random.nextInt(200) + 100
//
//                layoutParams = ViewGroup.LayoutParams(w, h)
//                setPadding(2.toPx, 2.toPx, 2.toPx, 2.toPx)
//            }
//
//            rowLayout.addView(customView)
//        }
    }

    companion object {
        @ColorInt
        private const val BRIGHT_GREEN = 0xff00ff00.toInt()

        @ColorInt
        private const val BRIGHT_RED = 0xffff0000.toInt()

        @ColorInt
        private const val BRIGHT_YELLOW = 0xffffff00.toInt()

        @ColorInt
        private const val BRIGHT_BLUE = 0xff0000ff.toInt()

        private val colors = arrayOf(BRIGHT_GREEN, BRIGHT_RED, BRIGHT_YELLOW, BRIGHT_BLUE)
    }
}
