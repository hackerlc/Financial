package com.boxin.financial.ui.financial.main

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 标的
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
open class FinancialPresenter(v: FinancialContract.View) :
        PresenterDataWrapper<String, FinancialContract.View>(v),
        FinancialContract.Presenter<String> {

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