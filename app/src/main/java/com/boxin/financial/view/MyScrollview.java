package com.boxin.financial.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/1/20.
 */

public class MyScrollview extends NestedScrollView {
    private static StopCall stopCall;

    //ScrollView向上滑动到顶部的距离
    private int upH;

    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);

        //赋值：300很重要，这个值是顶部2上面的高度，也就是本例中图片的高度
        upH = dpTopx(300);//单位是dp
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public static void setCallback(StopCall c) {
        stopCall = c;
    }

    /**
     * 关键部分在这里，测量当前ScrollView滑动的距离
     * 其中t就是，单位是px哦，不是dp
     * stopCall是一个接口，是为了在Activity中实现设置顶部1/2可不可见
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t > upH) {
            //如果滑动距离>本例中图片高度
            stopCall.stopSlide(true);//设置顶部1可见，顶部2不可见
        } else {
            stopCall.stopSlide(false);
        }
    }

    public interface StopCall {
        void stopSlide(boolean isStop);
    }


    private int dpTopx(int dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}