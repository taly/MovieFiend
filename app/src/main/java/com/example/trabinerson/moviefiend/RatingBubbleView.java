package com.example.trabinerson.moviefiend;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A container for a movie's rating.
 */
public class RatingBubbleView extends TextView {

    private Paint mBubblePaint;
    private Paint mBackgroundBubble1Paint;
    private Paint mBackgroundBubble2Paint;
    private Paint mTextPaint;

    private int mBubbleColor;
    private double mFinalRating;
    private String mBubbleText;

    public RatingBubbleView(Context context, AttributeSet attributes) {
        super(context, attributes);

        // Get attributes
        TypedArray a = context.getTheme().obtainStyledAttributes(attributes, R.styleable.RatingBubbleView, 0, 0);
        try {
            mBubbleColor = a.getColor(R.styleable.RatingBubbleView_bubbleColor, 0);
            mBubbleText = a.getString(R.styleable.RatingBubbleView_bubbleText);
        }
        finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(mBubbleColor);

        mBackgroundBubble1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundBubble1Paint.setColor(Color.DKGRAY);

        mBackgroundBubble2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundBubble2Paint.setColor(Color.WHITE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.DKGRAY);
        mTextPaint.setTextSize(45);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO CALCULATE THESE OUTSIDE!
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() / 2;
        float outerRadius = x;

        // Background
        canvas.drawCircle(x, y, outerRadius, mBackgroundBubble1Paint);
        canvas.drawCircle(x, y, outerRadius*0.9f, mBackgroundBubble2Paint);

        // Circle
        // TODO add padding
        canvas.drawCircle(x, y, outerRadius*0.85f, mBubblePaint);

        // Text
        float a = (mTextPaint.ascent() + mTextPaint.descent());
        canvas.drawText(mBubbleText, x + a, y - a / 2, mTextPaint);
    }

    public void setFinalRating(double rating) {
        mFinalRating = rating;
        mBubbleText = Double.toString(rating);
    }

    public void setRating(float rating) {
        mBubbleText = String.format("%.1f", rating);
        invalidate();
    }

    public void setBubbleColor(int color) {
        mBubbleColor = color;
        mBubblePaint.setColor(mBubbleColor);
        invalidate();
    }
}
