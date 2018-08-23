package com.boxin.financial.net.model

import com.boxin.financial.R.id.type
import com.boxin.financial.base.net.BaseNM
import com.boxin.financial.entity.*
import com.boxin.financial.net.api.ApiManager
import com.boxin.financial.net.helper.RxNetWorkHelper
import io.reactivex.Flowable

/**
 * 回款信息
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/4
 */
class RepaymentNM: BaseNM() {

    /**
     * @param 加载类型
     * @param page 页数
     * @param size 条数
     * @param type 0 待回款 1 已回款
     */
    fun fetchRepaymentList(type: Int = 0,page: Int,size: Int): Flowable<ResponseJson<BaseList<RepaymentGroup>>> {
        mRP.map.clear()
        mRP.map["pageNo"] = page
        mRP.map["pageSize"] = size
        mRP.map["repayment"] = type == 1
        return ApiManager.COMMON_API.getRepaymentInfo(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

    fun fetchRepaymentDetail(id: String): Flowable<ResponseJson<RepaymentDetail>> {
        mRP.map.clear()
        mRP.map["code"] = id
        return ApiManager.COMMON_API.getRepaymentDetail(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }
}