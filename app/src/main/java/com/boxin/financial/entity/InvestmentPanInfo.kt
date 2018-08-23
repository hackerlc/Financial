package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 回款计划
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class InvestmentPanInfo {
    /**
     * 回款总额
     */
    @SerializedName("total")
    var allMoney: String? = null
    /**
     * 回款本金
     */
    @SerializedName("amount")
    var money: String? = null
    /**
     * 回款收益
     */
    @SerializedName("income")
    var interest: String? = null
    /**
     * 回款日期
     */
    @SerializedName("repaymentDate")
    var date: String? = null
    /**
     * 回款状态
     */
    @SerializedName("repayment")
    var type: Boolean = false
}