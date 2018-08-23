package com.boxin.financial.net

import com.boxin.financial.base.App
import com.boxin.financial.config.APIConfig.Companion.BASE_URL
import gear.yc.com.gearlibrary.network.api.GearHttpServiceManager
import gear.yc.com.gearlibrary.network.http.OkHttpManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * 网络访问管理，提供全局的okClient和Retrofit引用
 * @author joker
 * Email:812405389@qq.com
 * @version 2017/11/6
 */
class NetManager {
    /**
     * 初始化Ok引用和Retrofit引用
     */
    companion object {
        var mClient : OkHttpClient = OkHttpManager.getInstance()
                .build(App.instance)
                .client
        var mRetrofit : Retrofit = GearHttpServiceManager.build(BASE_URL, mClient)
    }
}
