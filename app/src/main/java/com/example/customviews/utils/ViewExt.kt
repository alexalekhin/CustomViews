package com.example.customviews.utils

import android.content.res.Resources

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density + 0.5).toInt()

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5).toInt()