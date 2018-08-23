package com.boxin.financial.net.model

import com.boxin.financial.base.net.BaseNM
import com.boxin.financial.entity.*
import com.boxin.financial.net.api.ApiManager
import com.boxin.financial.net.helper.RxNetWorkHelper
import io.reactivex.Flowable

/**
 * 标的列表 info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/4
 */
class CommonNM: BaseNM() {

    /**
     * 获取首页数据
     */
    fun fetchHome(): Flowable<ResponseJson<HomeData>> {
        mRP.map.clear()
        return ApiManager.COMMON_API.fetchHome(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }/**
     * 更新app
     */
    fun upDataApp(): Flowable<ResponseJson<UpApp>> {
        mRP.map.clear()
        mRP.map["type"] = "android"
        return ApiManager.COMMON_API.updateApp(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }
}