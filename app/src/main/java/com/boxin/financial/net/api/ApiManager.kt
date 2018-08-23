package com.boxin.financial.net.api

import com.boxin.financial.net.NetManager

/**
 * API管理并提供接口引用
 * @author joker
 * Email:812405389@qq.com
 * @version 2017/11/8
 */
object ApiManager {
    val COMMON_API: CommonApi = NetManager.mRetrofit.create(CommonApi::class.java)
}