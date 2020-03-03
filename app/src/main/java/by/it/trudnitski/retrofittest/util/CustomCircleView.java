package by.it.trudnitski.retrofittest.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import by.it.trudnitski.retrofittest.R;

public class CustomCircleView extends View {
    private Paint mPaint;
    private int mColor;
    private float mRadius;
    private float mCircleX;
    private float mCircleY;
    private boolean showCircle;
    private int backgroundColor;

    public CustomCircleView(Context context) {
        super(context);
        init(null);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(AttributeSet set) {
        if (set != null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.CustomCircleView);
            mColor = ta.getColor(R.styleable.CustomCircleView_square_color, Color.MAGENTA);
            //mColor = R.attr.background;
            mRadius = ta.getDimensionPixelOffset(R.styleable.CustomCircleView_square_radius, 100);
            showCircle = ta.getBoolean(R.styleable.CustomCircleView_show_circle, true);
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        backgroundColor = Color.GRAY;
        mCircleX = this.getWidth() / 2;
        mCircleY = this.getHeight() / 2;
        mPaint.setColor(mColor);
        canvas.drawColor(backgroundColor);
        if (!showCircle) {
            mPaint.setColor(backgroundColor);
        }
        canvas.drawCircle(mCircleX, mCircleY, mRadius, mPaint);
    }
}