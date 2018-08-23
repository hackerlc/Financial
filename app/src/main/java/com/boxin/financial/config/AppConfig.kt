package com.boxin.financial.config

import com.boxin.financial.entity.User
import com.boxin.financial.entity.UserAccount
import com.boxin.financial.view.gestureview.LockPatternUtils

/**
 * App共用参数方法
 * @author joker
 * Email:812405389@qq.com
 * @version 2017/11/14
 */
open class AppConfig {
    companion object {
        val INFO_QUERY_ADDRESS = "全国"
        val INFO_ERROR_NOT_DATA = "暂无数据"
        val INFO_ERROR_NETWORK = "网络异常"
        var IS_LOGIN = false
        var I_USER :User? = null
        var I_ACCOUNT: UserAccount? = UserAccount()
        const val APP_PHONE = "0571-28120388"
        /**
         * 验证码错误记录
         */
        var PSD_FAST_CODE = 0
        var PSD_REGISTER_CODE = 0
        var PSD_PASSWORD_CODE = 0
        var PSD_CHANGE_OLD = 0
        var PSD_CHANGE_NEW = 0

        /**
         * 密码错误
         */
        val psdPhoneMap: MutableMap<String,Int> = HashMap()
        var psdNum = 0

        const val PAGE_SIZE = 10
        const val MD5_KEY = "&Lds#2xm!"
    }
}