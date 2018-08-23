package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 首页快讯
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class News {
    @SerializedName("id")
    var id: String? = ""
    @SerializedName("title")
    var title: String? = ""
    @SerializedName("content")
    var img: String? = ""
    @SerializedName("linkPath")
    var url: String? = ""
}