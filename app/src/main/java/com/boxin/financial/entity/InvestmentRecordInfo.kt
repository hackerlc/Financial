package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 投资信息
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class InvestmentRecordInfo {
    /**
     * id
     */
    @SerializedName("id")
    var id: String? = null
    /**
     * 标题
     */
    @SerializedName("name")
    var title: String? = null
    /**
     * 出借金额
     */
    @SerializedName("amount")
    var money: String? = null
    /**
     * 预期收益
     */
    @SerializedName("income")
    var interest: String? = null
    /**
     * 商品状态
     */
    @SerializedName("status")
    var type: NameAndText? = null
    /**
     * 出借时间
     */
    @SerializedName("date")
    var date: String? = null
    /**
     * 止息日
     */
    @SerializedName("endDate")
    var endDate: String? = null
    /**
     * 还款类型
     */
    @SerializedName("interestType")
    var interestType: NameAndText? = null
    var interestTypeStr: String = ""
        get() {
            return when(interestType?.name){
                "dayInterest" -> {
                    "天"
                }
                "monthInterest" -> {
                    "个月"
                }
                else -> {
                    ""
                }
            }
        }
    /**
     * 订单编号
     */
    @SerializedName("orderCode")
    var orderCode: String? = null
    /**
     * 商品编号
     */
    @SerializedName("assetCode")
    var productCode: String? = null
    /**
     * 年利率
     */
    @SerializedName("rate")
    var rate: Double = 0.0
    /**
     * 平台加息利率（年化）
     */
    @SerializedName("platformRaiseRate")
    var platformRaiseRate: Double = 0.0

    var proRate: String = "0.0"
    get() {
        return "${rate+platformRaiseRate}"
    }
    /**
     * 还款方式
     */
    @SerializedName("repaymentType")
    var refundType: NameAndText? = null
    /**
     * 周期数
     */
    @SerializedName("deadline")
    var timeLimit: String? = null
    /**
     * 起息日
     */
    @SerializedName("startDate")
    var startDate: String? = null
}