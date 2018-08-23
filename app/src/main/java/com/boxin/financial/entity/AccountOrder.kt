package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 充值、提现
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class AccountOrder{
    /**
     * 账户id
     */
    @SerializedName("accountId")
    var accountId: String? = ""
    /**
     * 创建时间
     */
    @SerializedName("createTime")
    var createTime: String? = null
    /**
     * 修改时间
     */
    @SerializedName("modifyTime")
    var modifyTime: String? = null
    /**
     * 订单id
     */
    @SerializedName("id")
    var id: String? = null
    /**
     * 订单金额
     */
    @SerializedName("money")
    var money: String? = null
    /**
     * 订单号
     */
    @SerializedName("no")
    var no: String? = null
    /**
     * 备注
     */
    @SerializedName("remark")
    var remark: String? = null
    /**
     * 平台服务费
     */
    @SerializedName("serviceCharge")
    var serviceCharge: String? = null
    /**
     * 第三方服务费
     */
    @SerializedName("serviceChargeTo")
    var serviceChargeTo: String? = null
    /**
     * 订单状态
     */
    @SerializedName("state")
    var state: String? = null
    /**
     * 类型
     */
    @SerializedName("type")
    var type: String? = null
}