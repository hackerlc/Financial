package com.boxin.financial.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 标的信息
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/28
 */
open class Financial() : Parcelable {
    /**
     * id
     */
    @SerializedName("code")
    var id: String? = null
    /**
     * 标的名称
     */
    @SerializedName("name")
    var name: String? = null
    /**
     * 总利率
     */
    @SerializedName("profit")
    var profit: String? = null
        get() {
            return "${moneyRateBo+platformRaiseRate}"
        }
    /**
     * 借款时间
     */
    @SerializedName("deadline")
    var time: String? = null
    /**
     * 类型
     */
    @SerializedName("repaymentType")
    var type: NameAndText? = null
    var typeStr: String? = null
        get() {
            return type?.text
        }
    /**
     *  到期类型
     */
    @SerializedName("type")
    var tips: NameAndText? = null
    /**
     * 标的百分比
     */
    @SerializedName("progress")
    var progress: Double = 0.0
    fun getPro(): Int{
        return (progress*100.00).toInt()
    }

    /**
     * 剩余金额
     */
    @SerializedName("lave")
    var lave: Double = 0.00
    get() {
        return allMoney - raise - freeze
    }
    /**
     * 已募集金额
     */
    @SerializedName("raise")
    var raise: Double = 0.00
    /**
     * 已募集金额
     */
    @SerializedName("freeze")
    var freeze: Double = 0.00
    /**
     * 总金额
     */
    @SerializedName("amount")
    var allMoney: Double = 0.00
    /**
     * 利息
     */
    @SerializedName("income")
    var income: Double = 0.00
    /**
     * 起投金额
     */
    @SerializedName("min")
    var startMoney: Double = 0.0
    /**
     * 起息时间
     */
    @SerializedName("interestDay")
    var startTime: NameAndText? = null
    var startTimeStr: String? = null
        get() {
            return "${startTime?.text},募集成功次日记息"
        }
    /**
     * 回款方式
     */
    @SerializedName("style")
    var style: String? = null
    /**
     * 转让条件
     */
    @SerializedName("transferCondition")
    var transferCondition: String? = null
    /**
     * 安全等级
     */
    @SerializedName("safetyLevel")
    var save: Int = 0
    /**
     * 用户余额
     */
    @SerializedName("myUserMoney")
    var myUserMoney: Double = 0.0
    /**
     * 链接
     */
    @SerializedName("url")
    var url: String? = null
    /**
     * 已满标未满标
     */
    @SerializedName("status")
    var loanState: NameAndText? = null
    /**
     * 日息月息
     */
    @SerializedName("interestType")
    var interestTypeBo: NameAndText? = null
    var interestTypeBoStr: String = ""
    get() {
        return when(interestTypeBo?.name){
            "dayInterest" -> {
                "天"
            }
            "monthInterest" -> {
                "个月"
            }
            else -> {
                ""
            }
        }
    }
    /**
     * 平台加息费率
     */
    @SerializedName("platformRaiseRate")
    var platformRaiseRate: Double = 0.00
    /**
     * 平台年利率
     */
    @SerializedName("yearPlatformRaiseRate")
    var yearPlatformRaiseRate: Double = 0.00
    /**
     * 利率
     */
    @SerializedName("rate")
    var moneyRateBo: Double = 0.00
    /**
     * 年利率
     */
    @SerializedName("yearRate")
    var yearRate: Double = 0.00

    var yearProfit: String? = null
        get() {
            return "${yearRate+yearPlatformRaiseRate}"
        }

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        time = parcel.readString()
        type = parcel.readParcelable(NameAndText::class.java.classLoader)
        tips = parcel.readParcelable(NameAndText::class.java.classLoader)
        progress = parcel.readDouble()
        raise = parcel.readDouble()
        freeze = parcel.readDouble()
        allMoney = parcel.readDouble()
        startMoney = parcel.readDouble()
        startTime = parcel.readParcelable(NameAndText::class.java.classLoader)
        style = parcel.readString()
        transferCondition = parcel.readString()
        save = parcel.readInt()
        myUserMoney = parcel.readDouble()
        url = parcel.readString()
        loanState = parcel.readParcelable(NameAndText::class.java.classLoader)
        interestTypeBo = parcel.readParcelable(NameAndText::class.java.classLoader)
        platformRaiseRate = parcel.readDouble()
        yearPlatformRaiseRate = parcel.readDouble()
        moneyRateBo = parcel.readDouble()
        yearRate = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(time)
        parcel.writeParcelable(type, flags)
        parcel.writeParcelable(tips, flags)
        parcel.writeDouble(progress)
        parcel.writeDouble(raise)
        parcel.writeDouble(freeze)
        parcel.writeDouble(allMoney)
        parcel.writeDouble(startMoney)
        parcel.writeParcelable(startTime, flags)
        parcel.writeString(style)
        parcel.writeString(transferCondition)
        parcel.writeInt(save)
        parcel.writeDouble(myUserMoney)
        parcel.writeString(url)
        parcel.writeParcelable(loanState, flags)
        parcel.writeParcelable(interestTypeBo, flags)
        parcel.writeDouble(platformRaiseRate)
        parcel.writeDouble(yearPlatformRaiseRate)
        parcel.writeDouble(moneyRateBo)
        parcel.writeDouble(yearRate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Financial> {
        override fun createFromParcel(parcel: Parcel): Financial {
            return Financial(parcel)
        }

        override fun newArray(size: Int): Array<Financial?> {
            return arrayOfNulls(size)
        }
    }

}