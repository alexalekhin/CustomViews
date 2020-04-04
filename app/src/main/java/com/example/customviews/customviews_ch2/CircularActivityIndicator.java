package com.example.customviews.customviews_ch2;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;

public class CircularActivityIndicator extends View {

    @ColorInt
    private static final int PRESSED_FG_COLOR = 0xff0000ff;
    private static final int DEFAULT_FG_COLOR = 0xffff0000;
    @ColorInt
    private static final int DEFAULT_BG_COLOR = 0xffa0a0a0;

    private Paint foregroundPaint = new Paint();
    private Paint backgroundPaint = new Paint();
    private int selectedAngle;

    private Path clipPath;

    private boolean isPressed = false;
    private float previousX, previousY;

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
        foregroundPaint.setColor(isPressed ? PRESSED_FG_COLOR : DEFAULT_FG_COLOR);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(VIEW_LOG_TAG, "touch: " + event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                changePressed(true);

                previousX = event.getX();
                previousY = event.getY();
                return true;

            case MotionEvent.ACTION_UP:
                changePressed(false);
                return true;

            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                float currentY = event.getY();

                selectedAngle = getNewAngle(currentX, currentY);
                invalidate();
                return true;

            default:
                return false;
        }
    }

    private int getNewAngle(float x, float y) {
        x -= (float) getWidth() / 2;
        y -= (float) getHeight() / 2;

        int angle = (int) (180.0 * Math.atan2(y, x) / Math.PI) + 90;
        return (angle > 0) ? angle : 360 + angle;
    }

    private void changePressed(boolean isPressed) {
        this.isPressed = isPressed;
        invalidate();
    }
}
