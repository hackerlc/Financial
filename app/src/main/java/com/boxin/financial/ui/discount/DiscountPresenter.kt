package com.boxin.financial.ui.discount

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 折扣
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class DiscountPresenter(v: DiscountContract.View) :
        PresenterDataWrapper<String, DiscountContract.View>(v),
        DiscountContract.Presenter<String> {
    override fun fetch() {
    }

    override fun refreshData() {
    }

    override fun getData(): String {
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