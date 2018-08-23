package com.boxin.financial.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * class info
 *
 * @author joker
 *         Email:812405389@qq.com
 * @version 2018/4/8
 */
public class XScrollView extends ScrollView {
    public XScrollView(Context context) {
        this(context, null);
    }

    public XScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY != 0 && null != mOnScrollToBottomListener) {
            mOnScrollToBottomListener.onScrollBottomListener(clampedY);
        }
    }

    public OnScrollToBottomListener mOnScrollToBottomListener;

    public interface OnScrollToBottomListener{
        void onScrollBottomListener(boolean isBottom);
    }

    public void setOnScrollToBottomLintener(OnScrollToBottomListener mOnScrollToBottomListener) {
        this.mOnScrollToBottomListener = mOnScrollToBottomListener;
    }
}
