package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 首页数据组
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class HomeDetail {
    /**
     * banner
     */
    @SerializedName("banners")
    var banners: MutableList<Banner> = ArrayList()
    /**
     * 快讯
     */
    @SerializedName("news")
    var news: MutableList<News> = ArrayList()
    /**
     * 新手标名称
     */
    @SerializedName("name")
    var name: String? = null
    /**
     * 时间
     */
    @SerializedName("time")
    var time: String? = null
    /**
     * 利率
     */
    @SerializedName("interestRate")
    var interestRate: String? = null
    /**
     * id
     */
    @SerializedName("id")
    var id: String? = null
    /**
     * 推荐标的
     */
    @SerializedName("finances")
    var financails: MutableList<Financial> = ArrayList()
}