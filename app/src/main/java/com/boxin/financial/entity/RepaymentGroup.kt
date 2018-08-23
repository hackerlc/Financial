package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 回款item
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class RepaymentGroup {
    /**
     * 名称
     */
    @SerializedName("date")
    var date: String? = null
    /**
     * 其他
     */
    @SerializedName("plans")
    var repaymentItem: MutableList<RepaymentItem> = ArrayList()
}