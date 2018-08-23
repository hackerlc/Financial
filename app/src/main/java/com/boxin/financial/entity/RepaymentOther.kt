package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/5/29
 */
class RepaymentOther {
    /**
     * 本金
     */
    @SerializedName("amount")
    var amount: Double = 0.0
    /**
     * 利息
     */
    @SerializedName("income")
    var income: Double = 0.0
    /**
     * 总金额
     */
    @SerializedName("total")
    var total: Double = 0.0
    /**
     * 回款日期
     */
    @SerializedName("repaymentDate")
    var repaymentDate: String? = null
}