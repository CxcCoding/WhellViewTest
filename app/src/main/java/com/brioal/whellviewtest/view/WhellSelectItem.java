package com.brioal.whellviewtest.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Brioal on 2016/4/6.
 */
public class WhellSelectItem {
    //TODO 添加是否绘制指示器
    private float mStartY; // 起点y坐标
    private int mWidth; //宽度
    private int mHeight; //高度
    private RectF mRectf = new RectF(); // 画笔的面积
    private RectF mIndicator = new RectF(); // 指示器的位置
    private int mTextSize = 35; // 文字大小
    private int mPadding;// 留白
    private int mPaddingTipRight = 10;

    private int mColor = Color.parseColor("#FDFDFE"); //选中界面的颜色
    private int mTextColor = Color.parseColor("#DE5601"); //选中文字的颜色

    private String mText; //文字内容
    private String mTip = null;// 提示内容

    private boolean isShowIndicator = false;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 画笔

    public WhellSelectItem(float mStartY, int mWidth, int mHeight, int mPadding, String mText, boolean isShowIndicator, String mTip) {
        this.mStartY = mStartY;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.mPadding = mPadding;
        this.mText = mText;
        this.isShowIndicator = isShowIndicator;
        this.mTip = mTip;
        init();
    }

    public void setmText(String mText) {
        this.mText = mText;
    }


    public void setmTip(String mTip) {
        this.mTip = mTip;
    }

    public String getmText() {
        return mText;
    }

    private void init() {
        mRectf.left = 0;
        mRectf.top = mStartY;
        mRectf.right = mWidth;
        mRectf.bottom = mStartY + mHeight;
        mIndicator.left = 0;
        mIndicator.right = 10;
        mIndicator.top = mStartY;
        mIndicator.bottom = mStartY + mHeight;
    }

    //获取起始y坐标
    public float getmStartY() {
        return mStartY;
    }

    //设置起始y坐标
    public void setmStartY(float mStartY) {
        this.mStartY = mStartY;
    }

    //绘制
    public void onDraw(Canvas canvas) {
        mPaint.setColor(mColor);
        canvas.drawRect(mRectf, mPaint);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        int textWidth = (int) mPaint.measureText(mText); // 获取文字的宽度
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        int baseLine = (int) (mRectf.centerY() + (metrics.bottom - metrics.top) / 2 - metrics.bottom); // 获取基线
        canvas.drawText(mText, mRectf.centerX() - textWidth / 2, baseLine, mPaint); // 居中绘制 TODO 看不太懂
        if (isShowIndicator) {
            canvas.drawRect(mIndicator, mPaint);
        }
        if (mTip != null) {
            int mTipWidth = (int) mPaint.measureText(mTip);
            canvas.drawText(mTip, mRectf.right - mTipWidth - mPaddingTipRight, baseLine, mPaint); // 居中绘制 TODO 看不太懂
        }
    }
}
