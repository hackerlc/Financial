package com.boxin.financial.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * class info
 *
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/5/2
 */
public class MyWebView extends WebView {
    public MyWebView(Context context) {
        this(context,null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
