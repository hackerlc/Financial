package com.boxin.financial.entity

import com.boxin.financial.utils.CommonUtils
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 用户账户
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class UserAccount: Serializable{
    /**
     * 电话号码
     */
    @SerializedName("phone")
    var phone: String? = ""
    /**
     * 电话号码
     */
    @SerializedName("idCard")
    var idCard: String? = ""
    /**
     * 冻结资金
     */
    @SerializedName("blockingMoney")
    var blockingMoney: String? = ""
    /**
     * 累积收益
     */
    @SerializedName("earningsMoney")
    var earningsMoney: String? = null
    /**
     * 每月免费提现次数
     */
    @SerializedName("freeOut")
    var freeOut: Int = 0
    /**
     * 账户id
     */
    @SerializedName("id")
    var id: String? = null
    /**
     * 是否开户
     */
    @SerializedName("open")
    var open: Boolean = false
    /**
     * 真实姓名
     */
    @SerializedName("realName")
    var realName: String? = null
    /**
     * 汇付账号
     */
    @SerializedName("thirdAccount")
    var thirdAccount: String? = null
    /**
     * 可用资金
     */
    @SerializedName("money")
    var money: Double = 0.0
    fun moneyShow(): String{
        return CommonUtils.onFormatTwo(money)
    }
    /**
     * 资产总额
     */
    @SerializedName("totalMoney")
    var totalMoney: String? = null
    /**
     * 银行卡信息
     */
    @SerializedName("bankinfo")
    var bankInfos: MutableList<UserBank> = ArrayList()

    fun bankInfo(): UserBank?{
        if (bankInfos.size <= 0){
            return null
        }
        return bankInfos[0]
    }

}