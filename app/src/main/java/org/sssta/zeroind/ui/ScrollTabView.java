package org.sssta.zeroind.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import org.sssta.zeroind.R;

/**
 * Created by Heaven on 2015/11/1.
 */
public class ScrollTabView extends View {
    int mPosition;
    int mTabNum = 1;
    float mOffset, mTabWidth;
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public void setmTabNum(int mTabNum) {
        this.mTabNum = mTabNum;
    }
    public ScrollTabView(Context context) {
        super(context);
    }

    public ScrollTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setPosition(int position,float offset){
        if (offset == 0) {
            return;
        }
        mPosition = position;
        mOffset = offset;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTabWidth == 0) {
            mTabWidth = getWidth() / mTabNum;
        }
        float left = (mPosition + mOffset) * mTabWidth;
        final float right = left + mTabWidth;
        final float top = getPaddingTop();
        final float bottom = getHeight() - getPaddingBottom();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
