package com.boxin.financial.ui.financial.info

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.entity.Financial

/**
 * 标的详情信息
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialInfoPresenter(v: FinancialInfoContract.View) :
        PresenterDataWrapper<Financial, FinancialInfoContract.View>(v),
        FinancialInfoContract.Presenter<Financial> {
    override fun fetch() {
    }

    override fun refreshData() {
    }

    override fun getData(): Financial {
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