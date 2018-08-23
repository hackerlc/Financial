package com.boxin.financial.extended

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.boxin.financial.R

/**
 * activity  fragment  方法扩展类
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/23
 */

/**
 * activity 控件绑定
 * @param click 点击事件
 * @param views 控件集合
 */
fun Activity.onClickBind(click: View.OnClickListener, vararg views: View) {
    for(view in views){
        view.setOnClickListener(click)
    }
}

/**
 * 当头部颜色为灰色的时候
 * @param head 头部背景控件
 * @param title 标题文字颜色
 * @param leftImg 左侧图片
 */
fun Activity.onHeadChange(head: View,title: TextView,titleStr: String,leftImg: View) {
    head.setBackgroundColor(ContextCompat.getColor(this,R.color.colorBackground))
    title.setTextColor(ContextCompat.getColor(this,R.color.colorTextMain_Title))
    onInitHead(title,titleStr,leftImg)
}

/**
 * 标题文字修改，显示左侧图片
 */
fun Activity.onInitHead(title: TextView,titleStr: String,leftImg: View){
    title.text = titleStr
    leftImg.visibility = View.VISIBLE
}

/**
 * V4 Fragment 控件绑定
 * @param click 点击事件
 * @param views 控件集合
 */
fun android.support.v4.app.Fragment.onClickBind(click: View.OnClickListener, vararg views: View){
    for(view in views){
        view.setOnClickListener(click)
    }
}
/**
 * Fragment 控件绑定
 * @param click 点击事件
 * @param views 控件集合
 */
fun Fragment.onClickBind(click: View.OnClickListener, vararg views: View){
    for(view in views){
        view.setOnClickListener(click)
    }
}