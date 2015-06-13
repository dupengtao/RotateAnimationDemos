package com.example.dpt.rotateanimdemos.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;

/**
 * Created by dupengtao on 15-5-12.
 */
public class SimpleLeLoadingView extends View implements ValueAnimator.AnimatorUpdateListener {

    private static final int ROTATE_DURATION = 1000;
    private static final int DURATION = 1000;
    private static final int BALL_NUM = 6;
    private static final int DURATION2 = 100;
    private static int PERCENT_OFFSET = DURATION / 6 * 2;
    private static int EVERY_DURATION = DURATION / 6 - PERCENT_OFFSET / 6;
    //private static int EVERY_DURATION = DURATION / 6;
    private float mBallRadius, mViewSize, mViewRadius;
    private ArrayList<BallsLoadingShapeHolder> mBalls = new ArrayList<>(6);
    private ArrayList<Integer> colorList = new ArrayList<>(6);
    private AnimatorSet appearAnim, disappearAnim;
    private ObjectAnimator[] mAppearAnimators;
    private long mLastPercent;
    private ObjectAnimator rotateAnim;

    //new
    private float rot;

    public SimpleLeLoadingView(Context context) {
        this(context, null);
    }

    public SimpleLeLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] attrsArray = new int[]{
                android.R.attr.layout_width, // 0
                android.R.attr.layout_height // 1
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        int layout_width = ta.getDimensionPixelSize(0, ViewGroup.LayoutParams.MATCH_PARENT);
        int layout_height = ta.getDimensionPixelSize(1, ViewGroup.LayoutParams.MATCH_PARENT);
        ta.recycle();
        init(layout_width, layout_height);
    }


    private void init(int layout_width, int layout_height) {
        colorList.addAll(getDefaultColorList());
        prepare(layout_width, layout_height);
        preAnim();
    }

    public ArrayList<Integer> getDefaultColorList() {
        ArrayList<Integer> colorList = new ArrayList<>(6);
        colorList.add(Color.parseColor("#ed1e20"));
        colorList.add(Color.parseColor("#6c24c6"));
        colorList.add(Color.parseColor("#1ab1eb"));
        colorList.add(Color.parseColor("#8ad127"));
        colorList.add(Color.parseColor("#ffd800"));
        colorList.add(Color.parseColor("#ff8a00"));
        return colorList;
    }

    private void initBall() {
        float angleUnit = 360f / BALL_NUM;
        float drawRadius = mViewRadius - mBallRadius;
        for (int i = 0; i < BALL_NUM; i++) {
            PointF pointF = new PointF();
            pointF.set((float) (mViewSize / 2 + drawRadius * Math.sin(i * angleUnit * Math.PI / 180)),
                    (float) (mViewSize / 2 - drawRadius * Math.cos(i * angleUnit * Math.PI / 180)));
            mBalls.add(addBall(pointF.x, pointF.y, colorList.get(i)));
        }
    }


    private BallsLoadingShapeHolder addBall(float x, float y, int color) {
        OvalShape circle = new OvalShape();
        circle.resize(mBallRadius, mBallRadius);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        BallsLoadingShapeHolder shapeHolder = new BallsLoadingShapeHolder(drawable);
        shapeHolder.setX(x);
        shapeHolder.setY(y);
        Paint paint = drawable.getPaint();
        paint.setColor(color);
        shapeHolder.setPaint(paint);
        //shapeHolder.setAlpha(0f);
        return shapeHolder;
    }


    private void preAnim() {
        rotateAnim = preRotateAnim2();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (rotateAnim.isRunning()) {
            canvas.rotate(rot, canvas.getWidth() / 2, canvas.getHeight() / 2);
        }

        for (BallsLoadingShapeHolder ball : mBalls) {
            if (ball.getShape().getAlpha() <= 0) {
                continue;
            }
            canvas.translate(ball.getX() - mBallRadius / 2, ball.getY() - mBallRadius / 2);
            ball.getShape().draw(canvas);
            canvas.translate(-ball.getX() + mBallRadius / 2, -ball.getY() + mBallRadius / 2);
        }
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    private void prepare(int w, int h) {
        //int h = getMeasuredHeight();
        //int w = getMeasuredWidth();
        int size = h >= w ? h : w;
        mViewSize = size;
        mBallRadius = size / (192 / 24);
        mViewRadius = size / (192 / 96);
        initBall();
    }

    private ObjectAnimator preRotateAnim2() {
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rot",
                0, 360);
        ObjectAnimator rotateAnim = ObjectAnimator.ofPropertyValuesHolder(this, rotation).setDuration(ROTATE_DURATION);
        rotateAnim.setRepeatCount(-1); // -1:Infinite loop
        rotateAnim.setInterpolator(new LinearInterpolator());
        rotateAnim.addUpdateListener(this);
        return rotateAnim;
    }


    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        //invalidate();
        int x = (int) getX() + 1;
        int y = (int) getY() + 1;
        invalidate(x, y, x + getWidth(), y + getHeight());
    }

    public float getRot() {
        return rot;
    }

    public void setRot(float rot) {
        this.rot = rot;
    }

    public void testRotateAnimStart() {
        if (rotateAnim.isPaused()) {
            rotateAnim.resume();
        } else if (!rotateAnim.isRunning()) {
            rotateAnim.start();
        }else {
            rotateAnim.pause();
        }
    }

}
