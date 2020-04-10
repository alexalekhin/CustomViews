package com.example.customviews.customviews_ch4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

public class PrimitiveDrawingView extends View {

    @ColorInt
    private static final int DEFAULT_FG_COLOR = 0xffa0a0a0;
    @ColorInt
    private static final int DEFAULT_BG_COLOR = 0xff000221;
    @ColorInt
    private static final int BLACK_COLOR = 0xff000000;
    private static final int WHITE_COLOR = 0xffffffff;

    public static final int POINTS_NUM = 20;

    private Paint paint = new Paint();
    private float[] pointsArray;

    public PrimitiveDrawingView(Context context) {
        super(context);
        init();
    }

    public PrimitiveDrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(DEFAULT_BG_COLOR);

        if (pointsArray == null) {
            pointsArray = new float[POINTS_NUM * 2];
            for (int i = 0; i < POINTS_NUM; i++) {
                pointsArray[i * 2] = (float) Math.random() * getWidth();
                pointsArray[i * 2 + 1] = (float) Math.random() * getHeight();
            }
        }

        paint.setColor(DEFAULT_FG_COLOR);
        paint.setStrokeWidth(4.f);
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLines(pointsArray, paint);

        paint.setColor(WHITE_COLOR);
        paint.setStrokeWidth(10.f);
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoints(pointsArray, paint);
    }
}
