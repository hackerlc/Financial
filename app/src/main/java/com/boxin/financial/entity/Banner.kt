package com.boxin.financial.entity

import com.google.gson.annotations.SerializedName

/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
class Banner {
    @SerializedName("id")
    var id: String? = ""
    @SerializedName("fileName")
    var title: String? = ""
    @SerializedName("filePath")
    var img: String? = ""
    @SerializedName("linkPath")
    var url: String? = ""

}