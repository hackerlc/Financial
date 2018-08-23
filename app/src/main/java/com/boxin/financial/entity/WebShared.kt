package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/7/5
 */

open class WebShared{
    var url: String? = ""
    var title: String? = ""
    @SerializedName("dec")
    var des: String? = ""
}