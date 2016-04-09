package com.brioal.whellviewtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 类似小米新建闹钟选择时间的轮子
 * Created by Brioal on 2016/4/5.
 */
public class Wheel extends View {
    private static final String TAG = "WhellInfo";
    private int mWidth; // 组件的宽度
    private int mHeight; // 组件的高度
    private int mItemHeight; // item的高度
    private int mTextSizeNormal = 28; // 文字的大小
    private int mTextSizeSelect = 35; // 选中文字的代销
    private int mHeightUnSelect; // 未选中区域的高度,上下各一份
    private int mShowCount = 5; // 显示出来的item数量
    private int mPadding = 20; // 上下的留白

    private boolean mShowIndicator = false; // 是否显示左边的指示器


    private int mSelectIndex = 0; //当前的选中项

    private Paint mPaint; // 绘制文字的画笔
    private List<WhellItem> items = new ArrayList<>(); // 所有的item
    private WhellSelectItem itemSelect = null; //当前的选中项

    private int mColorSleectText = Color.parseColor("#DE5601");
    private int mColorSelectBack = Color.parseColor("#FDFDFE");

    private int mColorNormalText = Color.parseColor("#9F9F9F");
    private int mColorNormalBack = Color.parseColor("#F6F6F6");


    private onSelectItemListener onSelectItemListener;// 监听器器对象
    private float mTouchY; //手点击的Y坐标

    //监听器
    public interface onSelectItemListener {
        void onSelectItem(int mItem);
    }

    private List<String> mTexts;
    private String mTip = null;

    public Wheel(Context context) {
        this(context, null);
    }

    public Wheel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setmTip(String mTip) {
        this.mTip = mTip;
    }

    //设置数据源
    public void setmTexts(List<String> mTexts) {
        this.mTexts = mTexts;
    }

    //设置显示的数量
    public void setmShowCount(int mShowCount) {
        this.mShowCount = mShowCount;
    }

    //设置是否显示指示器
    public void setmShowIndicator(boolean mShowIndicator) {
        this.mShowIndicator = mShowIndicator;
    }

    //设置当前的选中项
    public void setmSelectIndex(int mSelectIndex) {
        this.mSelectIndex = mSelectIndex;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();//获取组件的宽度
        mPaint = new Paint();
        mPaint.setTextSize(mTextSizeNormal);
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        mItemHeight = (int) (metrics.bottom - metrics.top) + 2 * mPadding; // 获取item的高度
        mHeight = mItemHeight * (5);
        initWhellItems(mWidth, mItemHeight);
        //选中的绘制在正中间
        itemSelect = new WhellSelectItem((5 / 2) * mItemHeight, mWidth, mItemHeight, 1, mTexts.get(mSelectIndex), mShowIndicator, mTip);

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY));
    }

    private void initWhellItems(int mWidth, int mItemHeight) {
        items.clear();
        int start = (5 - mShowCount) / 2;
        int index = 0;
        for (int i = start; i < mShowCount; i++) {
            int mstartY = mItemHeight * (i); // 获取每一个item应该显示的起始点
            int mIndex = 2 - i + mSelectIndex; // 给给个item设置标示
            if (mIndex < 0) {
                mIndex = mTexts.size() + mIndex;
            }
            items.add(new WhellItem(mstartY, mWidth, mItemHeight, mTexts.get(mIndex)));
        }
        initItemAlpha();
    }

    public void initItemAlpha() {
        for (int i = 0; i < items.size(); i++) {
            WhellItem item = items.get(i);
            if (i == 0 || i == items.size() - 1) {
                item.setShader(true);
            } else {
                item.setShader(false);

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#F6F6F6"));
        for (WhellItem item : items) {
            item.onDraw(canvas);
        }
        itemSelect.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                System.out.println(items.get(0).getmStartY());
                mTouchY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float dy = event.getY() - mTouchY;
                mTouchY = event.getY();
                handlerMove(dy);
                break;

            case MotionEvent.ACTION_UP:
                handlerUp();
                break;
        }
        return true;

    }

    //处理抬起事件
    private void handlerUp() {
        float dy = mHeight / 2 - mItemHeight / 2 - items.get(5 / 2).getmStartY();
        for (int i = 0; i < items.size(); i++) {
            items.get(i).adjustY(dy);
        }
        adjust();
        invalidate();
    }

    //处理移动事件
    private void handlerMove(float dy) {

        for (int i = 0; i < items.size(); i++) {
            items.get(i).adjustY(dy);
        }
        adjust();
        invalidate();
    }

    private void adjust() {
        int index = -1;
        //处理往下滑自动填充头部
        if (items.get((5 - mShowCount) / 2).getmStartY() > mItemHeight / 2) { // 滑动部分大于1/2item高度
            index = mTexts.indexOf(items.get(0).getmText());
            //移除最后一个item用于重用
            WhellItem item = items.remove(items.size() - 1);
            //设置item的起始y坐标
            item.setmStartY(items.get(0).getmStartY() - mItemHeight);
            index = index - 1;
            Log.i(TAG, "adjust: " + index);
            if (index < 0) {
                index = mTexts.size() + index;
            }
            //设置文本
            item.setmText(mTexts.get(index));

            items.add(0, item);
            itemSelect.setmText(items.get(mShowCount / 2).getmText());
        }

        if (mHeight - items.get(items.size() - 1 - ((5 - mShowCount) / 2)).getmStartY() > mItemHeight) { // 滑动部分大于1/2item高度
            index = mTexts.indexOf(items.get(items.size() - 1).getmText());
            //移除最后一个item用于重用
            WhellItem item = items.remove(0);
            //设置item的起始y坐标
            item.setmStartY(items.get(items.size() - 1).getmStartY() + mItemHeight);
            index = index + 1;
            Log.i(TAG, "adjust: " + index);
            if (index > mTexts.size() - 1) {
                index = index - mTexts.size() + 1;
            }
            //设置文本
            item.setmText(mTexts.get(index));
            items.add(item);
            itemSelect.setmText(items.get(mShowCount / 2).getmText());
        }
        initItemAlpha();
        invalidate();
    }
}
