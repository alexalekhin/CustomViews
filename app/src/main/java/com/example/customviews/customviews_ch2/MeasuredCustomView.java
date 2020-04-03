package com.example.customviews.customviews_ch2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.example.customviews.R;

public class MeasuredCustomView extends View {

    public static final int DEFAULT_SIZE = 600;
    public static final int DEFAULT_FILL_COLOR = Color.BLACK;

    int[] colors;

    private boolean firstDraw = false;

    private Paint backgroundPaint = new Paint();

    private MeasuredCustomView(Builder builder) {
        super(builder.context);
        colors = new int[]{
                builder.topLeftColor,
                builder.topRightColor,
                builder.bottomLeftColor,
                builder.bottomRightColor
        };

        firstDraw = true;
    }

    public MeasuredCustomView(Context context) {
        super(context);

        init(context);
    }

    public MeasuredCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context) {
        backgroundPaint.setColor(DEFAULT_FILL_COLOR);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MeasuredCustomView, 0, 0);

        try {
            // Dropped in favor of Builder
        } finally {
            typedArray.recycle();
        }

        backgroundPaint.setColor(DEFAULT_FILL_COLOR);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.d(TAG, "width spec" + MeasureSpec.toString(widthMeasureSpec));
//        Log.d(TAG, "height spec" + MeasureSpec.toString(heightMeasureSpec));

        int width = getMeasurementSize(widthMeasureSpec, DEFAULT_SIZE);
        int height = getMeasurementSize(widthMeasureSpec, DEFAULT_SIZE + 200);

        setMeasuredDimension(width, height);
    }

    private static int getMeasurementSize(int measureSpec, int defaultSize) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                return size;

            case MeasureSpec.AT_MOST:
                return Math.min(measureSpec, defaultSize);

            case MeasureSpec.UNSPECIFIED:
            default:
                return defaultSize;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float left = 0f;
        float top = 0f;

        if (firstDraw) {
            LinearGradient linearGradient = new LinearGradient(0f, 0f, getWidth(), getHeight(), colors, null, Shader.TileMode.CLAMP);
            backgroundPaint.setShader(linearGradient);
            firstDraw = false;
        }

        canvas.drawRect(left + getPaddingStart(),
                top + getPaddingTop(),
                getWidth() - getPaddingEnd(),
                getHeight() - getPaddingBottom(),
                backgroundPaint
        );

        super.onDraw(canvas);
    }

    public static class Builder {
        private Context context;
        @ColorInt
        private int topLeftColor = DEFAULT_FILL_COLOR;
        @ColorInt
        private int topRightColor = DEFAULT_FILL_COLOR;
        @ColorInt
        private int bottomLeftColor = DEFAULT_FILL_COLOR;
        @ColorInt
        private int bottomRightColor = DEFAULT_FILL_COLOR;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder topLeftColor(@ColorInt int fillColor) {
            topLeftColor = fillColor;
            return this;
        }

        public Builder topRightColor(@ColorInt int fillColor) {
            topRightColor = fillColor;
            return this;
        }

        public Builder bottomLeftColor(@ColorInt int fillColor) {
            bottomLeftColor = fillColor;
            return this;
        }

        public Builder bottomRightColor(@ColorInt int fillColor) {
            bottomRightColor = fillColor;
            return this;
        }

        public MeasuredCustomView build() {
            return new MeasuredCustomView(this);
        }
    }
}
