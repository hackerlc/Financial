package com.boxin.financial.net.model

import com.boxin.financial.base.net.BaseNM
import com.boxin.financial.entity.ProductInfo
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
class ProductInfoNM: BaseNM() {

    /**
     * @param 加载类型
     * @param page 页数
     * @param size 条数
     */
    fun getData(type: Int,page: Int,size: Int): Flowable<ResponseJson<ProductInfo>> {
        mRP.map["type"] = type
        mRP.map["page"] = page
        mRP.map["size"] = size
        return ApiManager.COMMON_API.getProductInfo(mRP.map)
                .compose(RxNetWorkHelper.isNetWork())
    }

    fun getTestData(): ProductInfo{
        var datas = ProductInfo()
        datas.imgs = ArrayList()
        datas.imgs!!.add("http://imgtu.5011.net/uploads/content/20170411/9651441491896247.jpg")
        datas.imgs!!.add("http://imgsrc.baidu.com/imgad/pic/item/03087bf40ad162d9142f62d51bdfa9ec8b13cd32.jpg")
        datas.imgs!!.add("http://s14.sinaimg.cn/bmiddle/4e67c5ef62e54a853c06d")
        datas.imgs!!.add("http://img.lenovomm.com/s3/img/app/app-img-lestore/6508-2015-07-15103401-1436927641647.jpg")
        datas.imgs!!.add("http://s14.sinaimg.cn/bmiddle/4e67c5ef62e54a853c06d")
        return datas
    }

}