package com.test.chuanyi.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author qdafengzi
 * @date 2019/1/1 12:12 PM
 */
public class ArcProgressView extends View {
    private Paint mPaint;
    /**
     * 中心点的坐标
     */
    private int mX, mY;
    /**
     * 中间圆弧的画笔宽度
     */
    private float strokeWith = 30f;
    /**
     * 外圆弧的画笔宽度
     */
    private float outStrokeWith = 4f;
    /**
     * 内圆弧的画笔宽度
     */
    private float innerStrokeWith = 2f;
    /**
     * 中心圆的半径
     */
    float mR = 200;
    float mROut = mR + 24;
    float mRInner = mR - 24;
    /**
     * 两边耳朵的长度
     */
    float bothEarLength = 20f;

    /**
     * 大刻度线的个数
     */
    private int mMainCalibration = 8;
    /**
     * 小刻度线的个数
     */
    private int mSecondaryCalibration = 22;
    /**
     * 大刻度线长度
     */
    private int mMainCalibrationLength = 30;
    /**
     * 小刻度线长度
     */
    private int mSecondaryCalibrationLength = 8;

    /*
       从-15的地方开始画刻度
     */
    private double mStartAngle = -15;
    private double mSecondaryStartAngle = -15;

    /**
     * 开口大小
     */
    float openRadian = 150;
    /**
     * 画弧开始的角度位置
     */
    float startAngle = 165;
    /**
     * 画弧扫过的角度
     */
    float sweepAngle = 210;


    public ArcProgressView(Context context) {
        this(context, null);
    }

    public ArcProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWith);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mX = w / 2;
        mY = h / 2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画三个圆弧
        drawArcMiddleBackground(canvas);
        drawArcMiddle(canvas, 160.0f);
        drawArcOut(canvas);
        drawArcInner(canvas);
        //画刻度
        drawMainCalibration(canvas);
        drawSecondaryCalibration(canvas);
        //画文字
        drawText01(canvas, "Available Limit");
        drawText02(canvas, "6,753,400");
        drawText03(canvas, "Total limit");
        drawText04(canvas, "7,500,000");
    }

    private void drawArcMiddle(Canvas canvas, float sweepAngle) {
        LinearGradient linearGradient = new LinearGradient(
                (float) (mX - Math.sqrt((mR * mR - (mR / 2) * (mR / 2)))), mY + mR / 2,
                (float) (mX + Math.sqrt((mR * mR - (mR / 2) * (mR / 2)))), mY + mR / 2,
                Color.parseColor("#D9465E")
                , Color.parseColor("#2DA9F8")
                , Shader.TileMode.MIRROR);
        mPaint.setShader(linearGradient);
        RectF oval = new RectF(mX - mR, mY - mR, mX + mR, mY + mR);
        canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint);
        mPaint.setShader(null);
    }

    private void drawArcMiddleBackground(Canvas canvas) {
        //笔的圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.argb(255, 232, 242, 252));
        RectF oval = new RectF(mX - mR, mY - mR, mX + mR, mY + mR);
        canvas.drawArc(oval, startAngle, sweepAngle, false, mPaint);
    }

    private void drawArcInner(Canvas canvas) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#DEF1FD"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(innerStrokeWith);
        RectF ovalInner = new RectF(mX - mRInner, mY - mRInner, mX + mRInner, mY + mRInner);
        //startAngle是三点钟方向的角度0，逆时针到起始的角度120
        canvas.drawArc(ovalInner, startAngle - 3, sweepAngle + 6, false, mPaint);
    }

    private void drawArcOut(Canvas canvas) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#DEF1FD"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(outStrokeWith);
        RectF ovalOut = new RectF(mX - mROut, mY - mROut, mX + mROut, mY + mROut);
        canvas.drawArc(ovalOut, startAngle, sweepAngle, false, mPaint);
        //最外圈的两个边
        drawTwoBothSide(canvas);
    }

    /**
     * 画两边的耳朵
     *
     * @param canvas
     */
    private void drawTwoBothSide(Canvas canvas) {
        canvas.drawLine((float) (mX - mROut * Math.sin(Math.toRadians(openRadian / 2)))
                , (float) (mY + mROut * Math.cos(Math.toRadians(openRadian / 2)))
                , (float) (mX - mROut * Math.sin(Math.toRadians(openRadian / 2)) - bothEarLength)
                , (float) (mY + mROut * Math.cos(Math.toRadians(openRadian / 2))), mPaint);

        canvas.drawLine((float) (mX + mROut * Math.sin(Math.toRadians(openRadian / 2)))
                , (float) (mY + mROut * Math.cos(Math.toRadians(openRadian / 2)))
                , (float) (mX + mROut * Math.sin(Math.toRadians(openRadian / 2)) + bothEarLength)
                , (float) (mY + mROut * Math.cos(Math.toRadians(openRadian / 2))), mPaint);
    }

    private void drawMainCalibration(Canvas canvas) {
        float startAngele = (float) Math.toRadians(mStartAngle);
        for (int i = 0; i < mMainCalibration; i++) {
            float startX = (float) (mX + ((mR - mMainCalibrationLength - strokeWith / 2) * Math.cos(startAngele)));
            float startY = (float) (mY - ((mR - mMainCalibrationLength - strokeWith / 2) * Math.sin(startAngele)));
            float stopX = (float) (mX + (mR - strokeWith / 2) * Math.cos(startAngele));
            float stopY = (float) (mY - (mR - strokeWith / 2) * Math.sin(startAngele));
            canvas.drawLine(startX, startY, stopX, stopY, mPaint);
            startAngele = (float) (startAngele + Math.toRadians(30));
        }
    }


    private void drawSecondaryCalibration(Canvas canvas) {
        float startAngele = (float) Math.toRadians(mSecondaryStartAngle);
        for (int i = 0; i < mSecondaryCalibration; i++) {
            float startX = (float) (mX + ((mR - mSecondaryCalibrationLength - strokeWith) * Math.cos(startAngele)));
            float startY = (float) (mY - ((mR - mSecondaryCalibrationLength - strokeWith) * Math.sin(startAngele)));
            float stopX = (float) (mX + (mR - strokeWith) * Math.cos(startAngele));
            float stopY = (float) (mY - (mR - strokeWith) * Math.sin(startAngele));
            canvas.drawLine(startX, startY, stopX, stopY, mPaint);
            startAngele = (float) (startAngele + Math.toRadians(10));
        }
    }

    private void drawText01(Canvas canvas, String text) {
        mPaint.reset();
        mPaint.setColor(Color.argb(255, 73, 171, 208));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(20f);
        mPaint.setTextScaleX(1.4f);
        canvas.drawText(text, mX, mY - mR / 2, mPaint);
    }

    private void drawText02(Canvas canvas, String text) {
        mPaint.setFakeBoldText(true);
        mPaint.setTextSize(40);
        mPaint.setColor(Color.argb(255, 29, 86, 166));
        canvas.drawText(text, mX, mY - mR / 6, mPaint);
        //测量"钱"所占的像素
        int width = (int) mPaint.measureText(text);
        mPaint.setFakeBoldText(false);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setColor(Color.argb(255, 73, 171, 208));
        mPaint.setTextSize(20);
        canvas.drawText("$", mX - width * 0.6f, mY - mR / 6, mPaint);
    }

    private void drawText03(Canvas canvas, String text) {
        mPaint.setColor(Color.argb(255, 205, 205, 205));
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, mX, mY + mR / 20, mPaint);
    }

    private void drawText04(Canvas canvas, String text) {
        mPaint.setFakeBoldText(true);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.argb(255, 29, 86, 166));

        canvas.drawText(text, mX, mY + mR / 4, mPaint);
        //测量"钱"所占的像素
        int width = (int) mPaint.measureText(text);

        mPaint.setFakeBoldText(false);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setColor(Color.argb(255, 73, 171, 208));
        mPaint.setTextSize(18);
        canvas.drawText("$", mX - width * 0.6f, mY + mR / 4, mPaint);
    }
}
