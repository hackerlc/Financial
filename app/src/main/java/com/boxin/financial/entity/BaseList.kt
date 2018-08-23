package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/5/15
 */
class BaseList<T> {
    /**
     * 页
     */
    @SerializedName("pageNo")
    var pageNo: Int? = 0
    /**
     * 数
     */
    @SerializedName("pageSize")
    var pageSize: Int? = 10
    /**
     * 统计
     */
    @SerializedName("totalCount")
    var totalCount: Int? = 0
    /**
     * 页数
     */
    @SerializedName("pageCount")
    var pageCount: Int? = 0

    var items: MutableList<T>? = null
}