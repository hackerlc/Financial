package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 投资信息
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class ProductInfo {
    /**
     * 项目说明
     */
    @SerializedName("info")
    var info: String? = null
    /**
     * 姓名
     */
    @SerializedName("name")
    var name: String? = null
    /**
     * 年龄
     */
    @SerializedName("age")
    var age: String? = null
    /**
     * 身份证号
     */
    @SerializedName("idcard")
    var idCard: String? = null
    /**
     * 户籍地址
     */
    @SerializedName("address")
    var address: String? = null
    /**
     * 主题性质
     */
    @SerializedName("type")
    var type: String? = null
    /**
     * 借款用途
     */
    @SerializedName("use")
    var use: String? = null
    /**
     * 借款用途
     */
    @SerializedName("imgs")
    var imgs: MutableList<String>? = ArrayList()
}