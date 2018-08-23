package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * 返回数据接收
 * @author joker
 * Email:812405389@qq.com
 * @version 2017/11/8
 */
open class ResponseJson<T> {
    @SerializedName("result")
    var result : T? = null

    var code: String = "0"
    //bread
    var message: String = ""

}