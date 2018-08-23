package com.boxin.financial.extended

import android.app.Activity
import android.app.Dialog
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.boxin.financial.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import gear.yc.com.gearlibrary.ui.custom.GearRecyclerItemDecoration
import gear.yc.com.gearlibrary.utils.ConvertPadPlus.dip2px


/**
 * view 扩展方法
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/17
 */

/**
 * 刷新控件扩展
 */
fun View.onDefaultSmartRefreshLayout() {
    if (this is SmartRefreshLayout) {
        initSmartRefreshLayout(R.color.colorBackground, R.color.colorTextMain_Title)
    }

}

/**
 * 刷新控件扩展
 * @param primaryColor 主题颜色
 * @param primaryTextColor 文字颜色
 */
fun View.initSmartRefreshLayout(primaryColor: Int,primaryTextColor: Int){
    if (this is SmartRefreshLayout) {
        setPrimaryColorsId(primaryColor, primaryTextColor)
        refreshHeader = ClassicsHeader(context)
                .setSpinnerStyle(SpinnerStyle.Translate)
        refreshFooter = ClassicsFooter(context)
                .setSpinnerStyle(SpinnerStyle.Translate)
    }
}

/**
 * RecycleView 初始化扩展
 */
fun  View.initRecyclerView(){
    if (this is RecyclerView){
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(GearRecyclerItemDecoration(context, LinearLayoutManager.VERTICAL,10))
    }
}

/**
 * 设置TabLayout指示器宽度
 * @param leftDP 左
 * @param rightDP 右
 */
fun View.setTabLayoutIndicatorWidth(leftDP: Int,rightDP: Int){
    if (this is TabLayout) {
        try {
            //拿到tabLayout的mTabStrip属性
            val mTabStrip = getChildAt(0) as LinearLayout

            val dpleft = dip2px(context, leftDP*1.0f)
            val dpright = dip2px(context, rightDP*1.0f)

            for (i in 0 until mTabStrip.childCount) {
                val tabView = mTabStrip.getChildAt(i)

                //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                val mTextViewField = tabView.javaClass.getDeclaredField("mTextView")
                mTextViewField.isAccessible = true

                val mTextView = mTextViewField.get(tabView) as TextView

                tabView.setPadding(0, 0, 0, 0)

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                var width: Int
                width = mTextView.width
                if (width == 0) {
                    mTextView.measure(0, 0)
                    width = mTextView.measuredWidth
                }

                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                val params = tabView.layoutParams as LinearLayout.LayoutParams
                params.width = width
                params.leftMargin = dpleft
                params.rightMargin = dpright
                tabView.layoutParams = params

                tabView.invalidate()
            }

        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

    }
}