package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 资金流水
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class CapitalFlow {
    /**
     * 名称
     */
    @SerializedName("flowType")
    var name: String? = ""
    fun nameStr(): String {
        return if (name.isNullOrEmpty()){
            "0.0"
        } else {
            return when(name){
                "normalWithdraw" -> "普通提现"
                "fastWithdraw" -> "快速提现"
                "returnMoney" -> "出借回款本金"
                "interestEarnings" -> "回款利息收益"
                "returnPenaltyManager" -> "回款逾期费"
                "withdrawBlocking" -> "提现冻结"
                "withdrawSuccess" -> "提现成功"
                "loanBlocking" -> "出借解冻"
                "investFrozen" -> "投资冻结"
                "investBlocking" -> "投资失败解冻"
                "recharge" -> "充值"
                "repayment" -> "还款本金"
                "repaymentInterest" -> "还款利息"
                "repaymentPlatformService" -> "还款平台服务费"
                "repaymentAccountService" -> "还款账户管理费"
                "repaymentInvestService" -> "还款投资服务费"
                "repaymentPlatformRaise" -> "还款平台加息费"
                "repaymentPenaltyDelay" -> "还款平台逾期费"
                "repaymentPenaltyManager" -> "还款逾期管理费"
                else -> {
                    "其他"
                }
            }
        }
    }
    /**
     * 时间
     */
    @SerializedName("createTime")
    var time: String? = ""
    /**
     * 金额
     */
    @SerializedName("money")
    var money: String? = ""
    fun moneyStr(): String {
        return if (expense.isNullOrEmpty() || money.isNullOrEmpty()){
            "0.0"
        } else {
            return when(expense){
                "in" -> "+$money"
                "out" -> "-$money"
                else -> money!!
            }
        }
    }

    /**
     * 消费状态
     */
    @SerializedName("expense")
    var expense: String? = ""

}