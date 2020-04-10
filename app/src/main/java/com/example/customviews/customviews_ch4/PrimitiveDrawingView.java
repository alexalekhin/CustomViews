package com.example.customviews.customviews_ch4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

import java.util.ArrayList;

public class PrimitiveDrawingView extends View {

    @ColorInt
    private static final int DEFAULT_FG_COLOR = 0xffff0000;
    @ColorInt
    private static final int DEFAULT_BG_COLOR = 0xffa0a0a0;
    @ColorInt
    private static final int BLACK_COLOR = 0xff000000;

    private Paint paint = new Paint();
    private ArrayList<Float> rectanglesList;
    private ArrayList<Integer> colors;

    public PrimitiveDrawingView(Context context) {
        super(context);
        init();
    }

    public PrimitiveDrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        rectanglesList = new ArrayList<>();
        colors = new ArrayList<>();

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(DEFAULT_BG_COLOR);

        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < 2; i++) {
            rectanglesList.add((float) Math.random() * width);
            rectanglesList.add((float) Math.random() * height);
        }

        colors.add(0xff000000 | (int) (0xffffff * Math.random()));

        for (int i = 0; i < rectanglesList.size() / 4; i++) {
            paint.setColor(colors.get(i));
            canvas.drawRoundRect(
                    rectanglesList.get(i * 4),
                    rectanglesList.get(i * 4 + 1),
                    rectanglesList.get(i * 4 + 2),
                    rectanglesList.get(i * 4 + 3),
                    40,
                    40,
                    paint
            );
        }

        if(rectanglesList.size() < 400) postInvalidateDelayed(20);
    }
}
