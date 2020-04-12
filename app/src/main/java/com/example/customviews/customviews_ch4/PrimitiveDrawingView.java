package com.example.customviews.customviews_ch4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;

import java.util.ArrayList;

public class PrimitiveDrawingView extends View {

    @ColorInt
    private static final int DEFAULT_FG_COLOR = 0xffff0000;
    @ColorInt
    private static final int DEFAULT_BG_COLOR = 0xff000221;
    @ColorInt
    private static final int BLACK_COLOR = 0xff000000;
    private static final int WHITE_COLOR = 0xffffffff;

    public static final int BEZIER_CURVE_POINTS_NUM = 3;

    private Paint pathPaint = new Paint();
    private Paint pointsPaint = new Paint();
    private Path path = new Path();
    private ArrayList<Float> pointList = new ArrayList<>();

    public PrimitiveDrawingView(Context context) {
        super(context);
        init();
    }

    public PrimitiveDrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setAntiAlias(true);
        pathPaint.setColor(WHITE_COLOR);
        pathPaint.setStrokeWidth(5.f);

        pointsPaint.setStyle(Paint.Style.STROKE);
        pointsPaint.setAntiAlias(true);
        pointsPaint.setColor(DEFAULT_FG_COLOR);
        pointsPaint.setStrokeCap(Paint.Cap.ROUND);
        pointsPaint.setStrokeWidth(40.f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pointList.add(event.getX());
            pointList.add(event.getY());

            invalidate();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(DEFAULT_BG_COLOR);

        int currentIndex = 0;

        while (pointList.size() - currentIndex >= BEZIER_CURVE_POINTS_NUM * 2) {
            float x1 = pointList.get(currentIndex);
            float y1 = pointList.get(currentIndex + 1);

            float x2 = pointList.get(currentIndex + 2);
            float y2 = pointList.get(currentIndex + 3);

            float x3 = pointList.get(currentIndex + 4);
            float y3 = pointList.get(currentIndex + 5);

            if (currentIndex == 0) {
                path.moveTo(x1, y1);
            }
            path.cubicTo(x1, y1, x2, y2, x3, y3);

            currentIndex += 6;
        }

        canvas.drawPath(path, pathPaint);

        for (int i = 0; i < pointList.size() / 2; i++) {
            float x = pointList.get(i * 2);
            float y = pointList.get(i * 2 + 1);

            canvas.drawPoint(x, y, pointsPaint);
        }
    }
}
