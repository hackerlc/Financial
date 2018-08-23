package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 用户信息
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class User:Serializable{
    /**
     * id
     */
    @SerializedName("id")
    var id: String? = ""
    /**
     * id
     */
    @SerializedName("token")
    var token: String? = ""
    /**
     * 电话
     */
    @SerializedName("phone")
    var phone: String? = null
    /**
     * 余额
     */
    @SerializedName("source")
    var money: String? = null
    /**
     * 是否实名认证
     */
    @SerializedName("isIdentification")
    var isIdentification: String? = null
    /**
     * 用户名
     */
    @SerializedName("name")
    var userName: String? = null
    /**
     * key
     */
    @SerializedName("key")
    var key: String? = null

    /**
     * 手势密码
     */
    var signLock = false
    /**
     * 指纹密码
     */
    var touchLock = false
}