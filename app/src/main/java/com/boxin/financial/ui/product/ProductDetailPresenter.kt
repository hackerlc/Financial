package com.boxin.financial.ui.product

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.entity.ProductInfo
import com.boxin.financial.net.model.ProductInfoNM

/**
 * 产品详情
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ProductDetailPresenter(v: ProductDetailContract.View) :
        PresenterDataWrapper<ProductInfo, ProductDetailContract.View>(v),
        ProductDetailContract.Presenter<ProductInfo> {
    init {
        mData = ProductInfo()
    }

    val mNm = ProductInfoNM()

    override fun fetch() {

        mData = mNm.getTestData()
        refreshData()
    }

    override fun refreshData() {
        mView.get()?.updateUI()
    }

    override fun getData(): ProductInfo {
        return mData
    }

    override fun errorData(error: Throwable) {
        super.errorData(error)
        mView.get()?.onError(error)
    }

    override fun close() {
        mView.clear()
    }
}