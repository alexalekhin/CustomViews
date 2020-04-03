package com.example.customviews.customviews_ch1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class OwnTextView extends AppCompatTextView {

    private Paint backgroundPaint;

    public OwnTextView(Context context) {
        super(context);

    }

    public OwnTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        backgroundPaint = new Paint();
        backgroundPaint.setColor(0xffffaa22);
        backgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0f, 0f, getWidth(), getHeight(), backgroundPaint);

        super.onDraw(canvas);
    }
}
