package com.example.trabinerson.moviefiend;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
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

    private static final int RATING_ANIMATION_DURATION = 1000;

    private float mTextShift;

    private Paint mBubblePaint;
    private Paint mBackgroundBubble1Paint;
    private Paint mBackgroundBubble2Paint;
    private Paint mTextPaint;

    private int mColor1;
    private int mColor2;
    private int mColor3;
    private String mBubbleText;

    public RatingBubbleView(Context context, AttributeSet attributes) {
        super(context, attributes);

        // Get attributes
        TypedArray a = context.getTheme().obtainStyledAttributes(attributes, R.styleable.RatingBubbleView, 0, 0);
        try {
            mColor1 = a.getColor(R.styleable.RatingBubbleView_color1, 0);
            mColor2 = a.getColor(R.styleable.RatingBubbleView_color2, 0);
            mColor3 = a.getColor(R.styleable.RatingBubbleView_color3, 0);
            mBubbleText = a.getString(R.styleable.RatingBubbleView_bubbleText);
        }
        finally {
            a.recycle();
        }

        init();
    }

    private void init() {

        // Paints
        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(mColor1);

        mBackgroundBubble1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundBubble1Paint.setColor(Color.DKGRAY);

        mBackgroundBubble2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundBubble2Paint.setColor(Color.WHITE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.DKGRAY);
        mTextPaint.setTextSize(45);

        // Dimensions
        mTextShift = mTextPaint.ascent() + mTextPaint.descent();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get dimensions
        int x = canvas.getWidth() / 2;
        int y = canvas.getHeight() / 2;
        float outerRadius = Math.min(x, y);

        // Background
        canvas.drawCircle(x, y, outerRadius, mBackgroundBubble1Paint);
        canvas.drawCircle(x, y, outerRadius * 0.9f, mBackgroundBubble2Paint);

        // Circle
        // TODO add padding
        canvas.drawCircle(x, y, outerRadius * 0.85f, mBubblePaint);

        // Text
        canvas.drawText(mBubbleText, x + mTextShift, y - mTextShift / 2, mTextPaint);
    }

    public void setRating(float rating, boolean animate) {
        if (animate && rating > 0) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(this, "ratingValue", 0f, rating);
            animator.setDuration(RATING_ANIMATION_DURATION);
            animator.start();
        }
        else {
            setRatingValue(rating);
        }
    }

    public void setRatingValue(float rating) {

        // Set text
        mBubbleText = String.format("%.1f", rating);

        // Set color
        int targetColor = ratingToColor(rating);
        mBubblePaint.setColor(targetColor);

        invalidate();
    }

    private int ratingToColor(float rating) {
        Integer targetColor;
        float normalizedRating;
        Integer color1 = new Integer(mColor1);
        Integer color2 = new Integer(mColor2);
        Integer color3 = new Integer(mColor3);
        ArgbEvaluator evaluator = new ArgbEvaluator();
        if (rating <= 5) {
            normalizedRating = rating / 5f;
            targetColor = (Integer) evaluator.evaluate(normalizedRating, color1, color2);
        }
        else {
            normalizedRating = (rating - 5f) / 5f;
            targetColor = (Integer) evaluator.evaluate(normalizedRating, color2, color3);
        }
        return targetColor;
    }
}
