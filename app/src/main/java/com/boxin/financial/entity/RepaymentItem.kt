package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 回款item
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class RepaymentItem {

    /**
     * id
     */
    @SerializedName("assetCode")
    var id: String? = null
    /**
     * 名称
     */
    @SerializedName("assetName")
    var name: String? = null
    /**
     * 回款时间
     */
    @SerializedName("paymentDate")
    var time: String? = null
    /**
     * 回款金额
     */
    @SerializedName("totalAmount")
    var money: String? = null
    /**
     * 付款类型
     */
    @SerializedName("repaymentType")
    var type: NameAndText? = null
    /**
     * 第几期回款
     */
    @SerializedName("currentIssue")
    var info: String? = null
    /**
     * 总回款期数
     */
    @SerializedName("totalIssue")
    var totalIssue: String? = null

}