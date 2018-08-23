package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 回款信息
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class RepaymentInfo {
    /**
     * 回款总额
     */
    @SerializedName("money")
    var money: String? = null

    /**
     * 列表信息
     */
    @SerializedName("list")
    var list:MutableList<RepaymentGroup> = ArrayList()
}