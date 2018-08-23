package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 首页数据
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/20
 */
class HomeData {
    /**
     * 标的列表
     */
    @SerializedName("recommandItemList")
    var financials: MutableList<Financial> = ArrayList()

    /**
     * 新手标数据
     */
    @SerializedName("noviceItem")
    var financial: Financial? = null

    /**
     * 轮播图
     */
    @SerializedName("broadcastImg")
    var banners: MutableList<Banner> = ArrayList()

    /**
     * 平台公告
     */
    @SerializedName("flashInfo")
    var news: MutableList<News> = ArrayList()
}