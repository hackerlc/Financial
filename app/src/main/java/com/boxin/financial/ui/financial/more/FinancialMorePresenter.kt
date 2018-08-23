package com.boxin.financial.ui.financial.more

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 标的详情更多信息
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialMorePresenter(v: FinancialMoreContract.View) :
        PresenterDataWrapper<String, FinancialMoreContract.View>(v),
        FinancialMoreContract.Presenter<String> {
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