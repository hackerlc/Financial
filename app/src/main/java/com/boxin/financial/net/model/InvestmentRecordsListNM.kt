package com.boxin.financial.net.model

import com.boxin.financial.base.net.BaseNM
import com.boxin.financial.entity.BaseList
import com.boxin.financial.entity.InvestmentRecordInfo
import com.boxin.financial.entity.ResponseJson
import com.boxin.financial.net.api.ApiManager
import com.boxin.financial.net.helper.RxNetWorkHelper
import io.reactivex.Flowable

/**
 * 投资信息列表
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/4
 */
class InvestmentRecordsListNM: BaseNM() {

    /**
     * @param 加载类型
     * @param page 页数
     * @param size 条数
     */
    fun getData(page: Int,size: Int): Flowable<ResponseJson<BaseList<InvestmentRecordInfo>>> {
        mRP.map.clear()
        mRP.map["pageNo"] = page
        mRP.map["pageSize"] = size
        return ApiManager.COMMON_API.getInvestmentRecordsList(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

    /**
     * @param 加载类型
     * @param page 页数
     * @param size 条数
     */
    fun getInvestmentRecords(id: String): Flowable<ResponseJson<InvestmentRecordInfo>> {
        mRP.map.clear()
        mRP.map["id"] = id
        return ApiManager.COMMON_API.getInvestmentRecords(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

}