package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 资金流水
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class CapitalFlowGroup {
    /**
     * 时间
     */
    @SerializedName("date")
    var date: String? = ""

    /**
     * 其他
     */
    @SerializedName("list")
    var capitalFlow: MutableList<CapitalFlow> = ArrayList()
}