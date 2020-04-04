package com.example.customviews.customviews_ch2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.ColorInt;

public class CircularActivityIndicator extends View {

    @ColorInt
    private static final int PRESSED_FG_COLOR = 0xff0000ff;
    private static final int DEFAULT_FG_COLOR = 0xffff0000;
    @ColorInt
    private static final int DEFAULT_BG_COLOR = 0xffa0a0a0;

    private Paint foregroundPaint = new Paint();
    private Paint backgroundPaint = new Paint();

    private int circleSize;

    private int selectedAngle;

    private Path clipPath;

    private boolean isPressed = false;

    private GestureDetector gestureDetector;
    private Scroller angleScroller;

    public CircularActivityIndicator(Context context) {
        super(context);
        init(context, DEFAULT_FG_COLOR, DEFAULT_BG_COLOR);
    }

    public CircularActivityIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, DEFAULT_FG_COLOR, DEFAULT_BG_COLOR);
    }

    private void init(Context context, @ColorInt int foregroundColor, @ColorInt int backgroundColor) {
        foregroundPaint.setColor(foregroundColor);
        foregroundPaint.setStyle(Paint.Style.FILL);

        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        selectedAngle = 280;

        angleScroller = new Scroller(context, null, true);
        angleScroller.setFinalX(selectedAngle);

        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            boolean isHandling;

            @Override
            public boolean onDown(MotionEvent e) {
                isHandling = processAngleUpdate(e.getX(), e.getY());

                if (isHandling) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    changePressed(true);
                    postInvalidate();
                }
                return isHandling;
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                endGesture();
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                isHandling = processAngleUpdate(e2.getX(), e2.getY());
                postInvalidate();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                endGesture();
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                endGesture();
                return false;
            }
        });
    }

    private void endGesture() {
        getParent().requestDisallowInterceptTouchEvent(false);
        changePressed(false);
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            endGesture();
            return true;
        } else {
            return gestureDetector.onTouchEvent(event);
        }
    }

    private boolean processAngleUpdate(float x, float y) {
        x -= (float) getWidth() / 2;
        y -= (float) getHeight() / 2;

        double radius = Math.sqrt(x * x + y * y);

        if (radius > (float) circleSize / 2 || radius < circleSize * 0.75 / 2)
            return false;

        int angle = (int) (180.0 * Math.atan2(y, x) / Math.PI) + 90;
        angle = (angle > 0) ? angle : 360 + angle;

        if (angleScroller.computeScrollOffset()) {
            angleScroller.forceFinished(true);
        }

        angleScroller.startScroll(angleScroller.getCurrX(), 0, angle - angleScroller.getCurrX(), 0);
        return true;
    }

    private void changePressed(boolean isPressed) {
        this.isPressed = isPressed;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boolean isScrollNotFinished = angleScroller.computeScrollOffset();
        selectedAngle = angleScroller.getCurrX();

        if (isScrollNotFinished) invalidate();

        foregroundPaint.setColor(isPressed ? PRESSED_FG_COLOR : DEFAULT_FG_COLOR);

        circleSize = getWidth();
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
