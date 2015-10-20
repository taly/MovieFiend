package com.example.trabinerson.moviefiend;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A container for a movie's rating.
 */
public class RatingBubbleView extends TextView {

    private Paint mOvalPaint;
    private int mBubbleColor;

    public RatingBubbleView(Context context) {
        super(context);
    }

    public RatingBubbleView(Context context, AttributeSet attributes) {
        super(context, attributes);

        // Get attributes
        TypedArray a = context.getTheme().obtainStyledAttributes(attributes, R.styleable.RatingBubbleView, 0, 0);
        try {
            mBubbleColor = a.getColor(R.styleable.RatingBubbleView_bubbleColor, 0);
        }
        finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mOvalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOvalPaint.setColor(mBubbleColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        // TODO add padding
        canvas.drawCircle(x, y, x, mOvalPaint);
    }

    public int getBubbleColor() {
        return mBubbleColor;
    }

    public void setBubbleColor(int color) {
        mBubbleColor = color;
        mOvalPaint.setColor(mBubbleColor);
        invalidate();
//        requestLayout();
    }
}
