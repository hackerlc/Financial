package com.boxin.financial.net.model

import com.boxin.financial.R.id.type
import com.boxin.financial.base.net.BaseNM
import com.boxin.financial.config.AppConfig.Companion.I_USER
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
class FinancialNM: BaseNM() {

    /**
     * 获取标的详情
     * @param 加载类型
     * @param page 页数
     * @param size 条数
     */
    fun getFinancialDetail(id: String): Flowable<ResponseJson<Financial>> {
        mRP.map.clear()
        mRP.map["code"] = id
        mRP.map["userId"] = "${I_USER?.id}"
        return ApiManager.COMMON_API.getFinancialDetail(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

    /**
     * @param hot 推荐商品 默认为true
     * @param type 加载类型 0 已完结 1募集中
     */
    fun getData(hot: Boolean = true,type: Int): Flowable<ResponseJson<MutableList<Financial>>> {
        mRP.map.clear()
        mRP.map["hot"] = hot
        when(type){
            0 -> mRP.map["status"] = "flush"
            1 -> mRP.map["status"] = "raiseing"
        }
        return ApiManager.COMMON_API.fetchFinancialList(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

    /**
     * 产品授权出借
     * @param lendMoneryVo 出借金额
     * @param loanIdVo 产品id
     */
    fun fetchAddLend(lendMoneryVo: String,loanIdVo: String): Flowable<ResponseJson<String>> {
        mRP.map.clear()
        mRP.map["lendMoneryVo"] = lendMoneryVo
        mRP.map["loanIdVo"] = loanIdVo
        return ApiManager.COMMON_API.fetchAddLend(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }
    /**
     * 获取标的详情
     * @param lendMoneryVo 出借金额
     * @param loanIdVo 产品id
     */
    fun payFinancialMoney(amount: String,code: String): Flowable<ResponseJson<String>> {
        mRP.map.clear()
        mRP.map["amount"] = amount
        mRP.map["code"] = code
        return ApiManager.COMMON_API.payFinancialMoney(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

}