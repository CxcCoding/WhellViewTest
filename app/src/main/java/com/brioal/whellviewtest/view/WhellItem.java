package com.brioal.whellviewtest.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * 条目类,绘制每一个item
 * Created by Brioal on 2016/4/6.
 */
public class WhellItem {
    //TODO 添加是否绘制蒙版
    private int mWidth; // 宽度
    private int mHegith; // 高度
    private float mStartY;// 起始的Y坐标
    private int mTextSize = 28;//文字大小
    private RectF rectF = new RectF();//绘制的坐标


    private int mTextCoxlor = Color.parseColor("#9F9F9F"); //文字的颜色
    private int mBackColor = Color.parseColor("#F6F6F6"); //文字的颜色
    private String mText; // 文字内容

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // 抗锯齿的画笔

    private boolean isShader; // 是否绘制阴影


    public WhellItem(float mStartY, int mWidth, int mHegith, String mText) {
        this.mStartY = mStartY;
        this.mWidth = mWidth;
        this.mHegith = mHegith;
        this.mText = mText;
        adjustY(0);
    }

    //设置文字
    public void setmText(String mText) {
        this.mText = mText;

    }

    public void setmTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public void setmBackColor(int mBackColor) {
        this.mBackColor = mBackColor;
    }

    public void setmTextCoxlor(int mTextCoxlor) {
        this.mTextCoxlor = mTextCoxlor;
    }

    public boolean isShader() {
        return isShader;
    }

    public void setShader(boolean shader) {
        isShader = shader;

    }

    public String getmText() {
        return mText;
    }

    //根据偏移坐标来调整item的位置
    public void adjustY(float mOffset) {
        mStartY += mOffset;
        rectF.left = 0;
        rectF.right = mWidth;
        rectF.top = mStartY;
        rectF.bottom = mStartY + mHegith;
    }

    //获取起始的y值
    public float getmStartY() {
        return mStartY;
    }

    //设置y坐标并调整ie,位置
    public void setmStartY(float mStartY) {
        this.mStartY = mStartY;
        rectF.left = 0;
        rectF.right = mWidth;
        rectF.top = mStartY;
        rectF.bottom = mStartY + mHegith;
    }

    public void onDraw(Canvas canvas) {
        mPaint.setColor(mBackColor);
        canvas.drawRect(rectF, mPaint);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextCoxlor);
        int textWidth = (int) mPaint.measureText(mText); // 获取文字的宽度
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        int baseLine = (int) (rectF.centerY() + (metrics.bottom - metrics.top) / 2 - metrics.bottom); // 获取基线
        if (isShader) {
            mPaint.setAlpha(80);
        }
        canvas.drawText(mText, rectF.centerX() - textWidth / 2, baseLine, mPaint);

    }
}
