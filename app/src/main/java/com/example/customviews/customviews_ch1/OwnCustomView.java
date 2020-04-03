package com.example.customviews.customviews_ch1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class OwnCustomView extends View {

    private Paint backgroundPaint;

    public OwnCustomView(Context context) {
        super(context);
        init();
    }

    public OwnCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xffffaa22);
        backgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(
                getPaddingLeft(),
                getPaddingTop(),
                getWidth() - getPaddingRight(),
                getHeight() - getPaddingBottom(),
                backgroundPaint
        );

        super.onDraw(canvas);
    }
}
