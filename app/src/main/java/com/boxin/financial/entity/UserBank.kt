package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 用户账户
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class UserBank{
    /**
     * 银行卡
     */
    @SerializedName("cardId")
    var cardId: String? = ""
    /**
     * code
     */
    @SerializedName("code")
    var code: String? = ""
    /**
     * name
     */
    @SerializedName("name")
    var name: String? = ""
    /**
     * default
     */
    @SerializedName("default")
    var default: Boolean = false
    /**
     * 图片
     */
    @SerializedName("logo")
    var logo: String? = ""
}