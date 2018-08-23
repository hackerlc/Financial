package com.boxin.financial.net.model

import com.boxin.financial.base.net.BaseNM
import com.boxin.financial.entity.*
import com.boxin.financial.net.api.ApiManager
import com.boxin.financial.net.helper.RxNetWorkHelper
import io.reactivex.Flowable

/**
 * 资金流水
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/4
 */
class CapitalFlowInfoNM: BaseNM() {

    /**
     * @param 加载类型
     * @param page 页数
     * @param size 条数
     */
    fun getData(page: Int,size: Int): Flowable<ResponseJson<BaseList<CapitalFlowGroup>>> {
        mRP.map["pageNo"] = page
        mRP.map["pageSize"] = size
        return ApiManager.COMMON_API.getCapitalFlow(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

}