package com.example.customviews.customviews_ch2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class RowLayout extends ViewGroup {

    public RowLayout(Context context) {
        super(context);
    }

    public RowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();

        int left = l + getPaddingLeft();
        int top = t + getPaddingTop();

        int rowHeight = 0;

        for (int idx = 0; idx < count; idx++) {
            View child = getChildAt(idx);

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (left + childWidth < r - getPaddingRight()) {
                child.layout(left, top, left + childWidth, top + childHeight);

                left += childWidth;
            } else {
                left = l + getPaddingLeft();
                top += rowHeight;

                rowHeight = 0;
            }

            if (childHeight > rowHeight)
                rowHeight = childHeight;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        int rowHeight = 0;
        int maxWidth = 0;
        int maxHeight = 0;

        int left = 0;
        int top = 0;

        for (int idx = 0; idx < count; idx++) {
            View child = getChildAt(idx);

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (left + childWidth < getWidth()) {
                left += childWidth;
            } else {
                if (left > maxWidth)
                    maxWidth = left;
                left = 0;
                top += rowHeight;
                rowHeight = 0;
            }

            if (childHeight > rowHeight)
                rowHeight = childHeight;
        }

        if (left > maxWidth)
            maxWidth = left;

        maxHeight = top + rowHeight;

        setMeasuredDimension(getMeasure(maxWidth, widthMeasureSpec), getMeasure(maxHeight, heightMeasureSpec));
    }


    private int getMeasure(int needed, int spec) {
        switch (MeasureSpec.getMode(spec)) {
            case MeasureSpec.EXACTLY:
                return MeasureSpec.getSize(spec);

            case MeasureSpec.AT_MOST:
                return Math.min(MeasureSpec.getSize(spec), needed);

            case MeasureSpec.UNSPECIFIED:
            default:
                return needed;
        }
    }
}
