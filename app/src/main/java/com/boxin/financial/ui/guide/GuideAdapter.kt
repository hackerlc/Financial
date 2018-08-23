package com.boxin.financial.ui.guide

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/23
 */
class GuideAdapter(private var views: MutableList<View>): PagerAdapter() {
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return views.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(views[position])
        return views[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(views[position])
    }

}