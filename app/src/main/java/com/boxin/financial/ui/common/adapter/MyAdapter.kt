package com.boxin.financial.ui.common.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/29
 */
class MyAdapter(fm: FragmentManager,val titles: MutableList<String>,val views: MutableList<Fragment>):
        FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return views[position]
    }

    override fun getCount(): Int {
        return views.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}