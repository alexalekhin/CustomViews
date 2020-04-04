package com.example.customviews.customviews_ch2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

public class CircularActivityIndicator extends View {

    @ColorInt
    private static final int DEFAULT_FG_COLOR = 0xffff0000;
    @ColorInt
    private static final int DEFAULT_BG_COLOR = 0xffa0a0a0;

    private Paint foregroundPaint = new Paint();
    private Paint backgroundPaint = new Paint();
    private int selectedAngle;

    private Path clipPath;

    public CircularActivityIndicator(Context context) {
        super(context);
        init(DEFAULT_FG_COLOR, DEFAULT_BG_COLOR);
    }

    public CircularActivityIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(DEFAULT_FG_COLOR, DEFAULT_BG_COLOR);
    }

    private void init(@ColorInt int foregroundColor, @ColorInt int backgroundColor) {
        foregroundPaint.setColor(foregroundColor);
        foregroundPaint.setStyle(Paint.Style.FILL);

        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        selectedAngle = 280;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int circleSize = getWidth();
        if (getHeight() < circleSize) {
            circleSize = getHeight();
        }

        int horizontalMargin = (getWidth() - circleSize) / 2;
        int verticalMargin = (getHeight() - circleSize) / 2;

        if (clipPath == null) {
            int clipWidth = (int) (circleSize * 0.75);

            int clipX = (getWidth() - clipWidth) / 2;
            int clipY = (getHeight() - clipWidth) / 2;

            clipPath = new Path();

            clipPath.addArc(
                    clipX,
                    clipY,
                    clipX + clipWidth,
                    clipY + clipWidth,
                    0,
                    360
            );
        }

        canvas.clipRect(0, 0, getWidth(), getHeight());
        canvas.clipPath(clipPath, Region.Op.DIFFERENCE);

        canvas.save();

        final int rotationAngle = -90;
        final int centerX = getWidth() / 2;
        final int centerY = getHeight() / 2;

        canvas.rotate(rotationAngle, centerX, centerY);

        canvas.drawArc(
                horizontalMargin,
                verticalMargin,
                circleSize + horizontalMargin,
                circleSize + verticalMargin,
                0,
                360,
                true,
                backgroundPaint
        );

        canvas.drawArc(
                horizontalMargin,
                verticalMargin,
                circleSize + horizontalMargin,
                circleSize + verticalMargin,
                0,
                selectedAngle,
                true,
                foregroundPaint
        );
    }
}
