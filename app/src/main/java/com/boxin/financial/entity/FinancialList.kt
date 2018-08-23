package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/19
 */
class FinancialList {
    /**
     * 推荐
     */
    @SerializedName("list")
    var list: MutableList<Financial> = ArrayList()
    /**
     * 已满标
     */
    @SerializedName("otherList")
    var otherList: MutableList<Financial> = ArrayList()
}