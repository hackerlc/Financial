package com.boxin.financial.extended

import android.app.Activity
import android.view.View
import com.boxin.financial.ui.home.HomeHeadViewHolder

/**
 * 其他类扩展
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/28
 */
/**
 * activity 控件绑定
 * @param click 点击事件
 * @param views 控件集合
 */
fun HomeHeadViewHolder.onClickBind(click: View.OnClickListener, vararg views: View) {
    for(view in views){
        view.setOnClickListener(click)
    }
}