package by.it.trudnitski.retrofittest.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import by.it.trudnitski.retrofittest.R;

public class CustomCircleView extends View {

    private Paint mPaint;
    private int mColor;
    private float mRadius;
    private float mCircleX, mCirclY;
    private boolean flag;
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
        if (set == null) {
            return;
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.CustomCircleView);
        //mColor = ta.getColor(R.styleable.CustomCircleView_square_color, Color.MAGENTA);
        mColor = R.attr.background;
        mRadius = ta.getDimensionPixelOffset(R.styleable.CustomCircleView_square_radius, 100);
        flag = ta.getBoolean(R.styleable.CustomCircleView_show_circle, true);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        backgroundColor = Color.GRAY;
        mCircleX = this.getWidth() / 2;
        mCirclY = this.getHeight() / 2;
        mPaint.setColor(mColor);
        canvas.drawColor(backgroundColor);
        if (!flag) {
            mPaint.setColor(backgroundColor);
        }
        canvas.drawCircle(mCircleX, mCirclY, mRadius, mPaint);

    }

    public void swapColor() {
        mPaint.setColor(mPaint.getColor() == mColor ? Color.GREEN : mColor);
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                return true;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                double dx = Math.pow(x - mCircleX, 2);
                double dy = Math.pow(y - mCirclY, 2);

                if (dx + dy < Math.pow(mRadius, 2)) {
                    mCircleX = x;
                    mCirclY = y;
                    postInvalidate();
                    return true;
                }
                return value;
        }
        return value;
    }
}
