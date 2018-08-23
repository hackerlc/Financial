package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 回款item
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class RepaymentDetail {
    /**
     * 名称
     */
    @SerializedName("name")
    var name: String? = null
    /**
     * 状态
     */
    @SerializedName("repayment")
    var type: Boolean = false
    /**
     * 回款汇总信息
     */
    @SerializedName("assetInfo")
    var assetInfo: Financial? = null
    /**
     * 已回款
     */
    @SerializedName("already")
    var already: RepaymentOther? = null
    /**
     * 已回款
     */
    @SerializedName("pending")
    var pending: RepaymentOther? = null
    /**
     * 回款计划
     */
    @SerializedName("plans")
    var repayments: MutableList<InvestmentPanInfo> = ArrayList()
}