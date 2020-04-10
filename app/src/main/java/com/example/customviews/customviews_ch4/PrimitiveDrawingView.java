package com.example.customviews.customviews_ch4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

import com.example.customviews.R;

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
    private Path path;

    private Bitmap backgroundBitmap;
    private Matrix backgroundMatrix = new Matrix();

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
        paint.setColor(WHITE_COLOR);

        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.harold);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(DEFAULT_BG_COLOR);

        if (path == null) {
            float[] pointsArray = new float[POINTS_NUM * 3];
            for (int i = 0; i < POINTS_NUM; i++) {
                pointsArray[i * 3] = (float) Math.random() * getWidth();
                pointsArray[i * 3 + 1] = (float) Math.random() * getHeight();
                pointsArray[i * 3 + 2] = (float) Math.random() * ((float) getWidth() / 4);
            }

            path = new Path();

            for (int i = 0; i < pointsArray.length / 3; i++) {
                path.addCircle(
                        pointsArray[i * 3],
                        pointsArray[i * 3 + 1],
                        pointsArray[i * 3 + 2],
                        Path.Direction.CW
                );
            }
            path.close();
        }

        canvas.save();

        canvas.clipPath(path);
        if (backgroundBitmap != null) {
            backgroundMatrix.reset();
            float scale = (float) getWidth() / backgroundBitmap.getWidth();
            backgroundMatrix.postScale(scale, scale);
            canvas.drawBitmap(backgroundBitmap, backgroundMatrix, null);
        }

        canvas.restore();
    }
}
