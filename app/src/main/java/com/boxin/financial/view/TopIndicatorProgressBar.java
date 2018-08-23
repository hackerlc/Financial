package com.boxin.financial.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.boxin.financial.R;


/**
 * 顶部带指示器的进度条
 * 只需要一个图片画笔一个文字画笔
 *
 * xml中使用方式
 * Created by YiChenZ(812405389) on 2018/4/10
 */

public class TopIndicatorProgressBar extends ProgressBar {
    /**
     * indicator default font size
     */
    protected static final int DEF_FONT_SIZE = 16;
    /**
     *     indicator default font color
     */
    protected static final int DEF_FONT_COLOR = 0xff666666;

    /**
     * top indicator height
     */
    protected int topHeight;
    protected int defHeight;

    /**
     * 变量
     */
    protected int progressWidth;
    /**
     * 顶部开始提示器变量
     */
    protected String topText;

    /**
     * 默认文字尺寸，颜色
     */
    protected int topTextColor;
    protected int topTextColor2;
    protected Drawable topDrawable;
    protected int topTextSize;

    /**
     * 绘制
     */
    protected Paint colorPaint;
    protected Paint textPaint;

    protected Context mContext;
    Bitmap bitMap = null;


    public TopIndicatorProgressBar(Context context) {
        this(context, null);
    }

    public TopIndicatorProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopIndicatorProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        obtainAttributes(attrs);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(topTextColor);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(topTextSize);
        textPaint.setFakeBoldText(true);
        colorPaint = new Paint(textPaint);
        colorPaint.setColor(topTextColor2);
        topHeight = (int)(textPaint.getTextSize() + textPaint.getFontMetrics().bottom);
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void obtainAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.TopIndicatorProgressBar);
        //顶部控件
        //获取文字
        topText = typedArray.getString(R.styleable.TopIndicatorProgressBar_topText);
        //获取默认文字尺寸、颜色、图片
        topTextSize = (int) typedArray.getDimension(R.styleable.TopIndicatorProgressBar_topTextSize,DEF_FONT_SIZE);
        topTextColor = typedArray.getColor(R.styleable.TopIndicatorProgressBar_topTextColor,DEF_FONT_COLOR);
        topTextColor2 = typedArray.getColor(R.styleable.TopIndicatorProgressBar_topColor,DEF_FONT_COLOR);
        topDrawable = typedArray.getDrawable(R.styleable.TopIndicatorProgressBar_topDrawable);
        bitMap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bg_progress_top);
        typedArray.recycle();
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        defHeight=MeasureSpec.getSize(heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        setMeasuredDimension(width, height);
        progressWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - bitMap.getWidth();
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable != null
                && progressDrawable instanceof LayerDrawable) {
            LayerDrawable d = (LayerDrawable) progressDrawable;

            for (int i = 0; i < d.getNumberOfLayers(); i++) {
                d.getDrawable(i).getBounds().left = 0;
                d.getDrawable(i).getBounds().top = topHeight;
                d.getDrawable(i).getBounds().right = progressWidth;
                d.getDrawable(i).getBounds().bottom = topHeight + defHeight;
            }
        }

        super.onDraw(canvas);
        canvas.save();
        setTopStartIndicator(canvas, progressDrawable);
        canvas.restore();
    }

    private void updateProgressBar() {
        Drawable progressDrawable = getProgressDrawable();

        if (progressDrawable != null
                && progressDrawable instanceof LayerDrawable) {
            LayerDrawable d = (LayerDrawable) progressDrawable;

            final float scale = getScale(getProgress());

            // get the progress bar and update it's size
            Drawable progressBar = d.findDrawableByLayerId(R.id.progress);

            final int width = d.getBounds().right - d.getBounds().left;

            if (progressBar != null) {
                Rect progressBarBounds = progressBar.getBounds();
                progressBarBounds.right = progressBarBounds.left
                        + (int) (width * scale + 0.5f);
                progressBar.setBounds(progressBarBounds);
            }

        }
    }

    private float getScale(int progress) {
        float scale = getMax() > 0 ? (float) progress / (float) getMax() : 0;

        return scale;
    }

    /**
     * 绘制指示器文字
     *
     * @param canvas
     * @param progressDrawable
     */
    private void setTopStartIndicator(Canvas canvas, Drawable progressDrawable) {
        canvas.translate(0, 0);
        // indicator start
        float xSize = progressWidth*(getProgress()*1.0f/getMax()) - ((float)bitMap.getWidth()/2f);
//        topText = xSize+"";
        Bitmap bitMap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.bg_progress_top);
        //图片
        canvas.drawBitmap(bitMap,xSize,0,new Paint(Paint.ANTI_ALIAS_FLAG));
        //文字
        float baseline = (topHeight / 2 + textPaint.getTextSize() / 2
                - textPaint.getFontMetrics().descent)
                + (bitMap.getHeight() - topHeight) /2;
        float tSize = (float) progressWidth*((float)getProgress()/(float)getMax()) - ((float)bitMap.getWidth()/2f);
        canvas.drawText(topText, tSize, baseline, textPaint);
        float tSize2 = (float) progressWidth*((float)getProgress()/(float)getMax());
        canvas.drawText(getMax() - getProgress() + "%", tSize2, baseline, colorPaint);

    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        result = specSize + bitMap.getHeight() + getPaddingTop() + getPaddingBottom() + dp2px(15);
        return result;
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        result = specSize + getPaddingLeft() + getPaddingRight();
        return result;
    }

    protected int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
    }

    public int getTopTextColor() {
        return topTextColor;
    }

    public void setTopTextColor(int topTextColor) {
        this.topTextColor = topTextColor;
    }

    public int getTopTextSize() {
        return topTextSize;
    }

    public void setTopTextSize(int topTextSize) {
        this.topTextSize = topTextSize;
    }

}
