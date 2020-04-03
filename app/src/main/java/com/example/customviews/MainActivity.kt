package com.example.customviews

import android.os.Bundle
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.example.customviews.customviews_ch2.MeasuredCustomView
import com.example.customviews.utils.UtilsJava
import com.example.customviews.utils.toPx
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityContent.addView(

            MeasuredCustomView.Builder(this)
                .topLeftColor(BRIGHT_GREEN)
                .topRightColor(BRIGHT_RED)
                .bottomLeftColor(BRIGHT_YELLOW)
                .bottomRightColor(BRIGHT_BLUE)
                .build()
                .apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    setPadding(
                        UtilsJava.dpToPixels(16),
                        UtilsJava.dpToPixels(16),
                        16.toPx,
                        16.toPx
                    )
                }
        )
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
    }
}
