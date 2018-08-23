package com.boxin.financial.net.model

import com.boxin.financial.base.net.BaseNM
import com.boxin.financial.entity.InvestmentPanInfo
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
class InvestmentPanListNM: BaseNM() {

    /**
     * @param 加载类型
     * @param page 页数
     * @param size 条数
     */
    fun getData(type: Int,page: Int,size: Int): Flowable<ResponseJson<MutableList<InvestmentPanInfo>>> {
        mRP.map["type"] = type
        mRP.map["page"] = page
        mRP.map["size"] = size
        return ApiManager.COMMON_API.getInvestmentPanList(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

    fun getTestData(datas: MutableList<InvestmentPanInfo>){
        datas.clear()
        var financial = InvestmentPanInfo()
        financial.date = "2010-00-00"
        financial.allMoney = "899999"
        financial.money = "9999"
        financial.interest = "1123"
        datas.add(financial)
        financial = InvestmentPanInfo()
        financial.date = "2010-11-00"
        financial.allMoney = "2222233"
        financial.money = "33"
        financial.interest = "22"
        datas.add(financial)
    }

}