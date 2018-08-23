package com.boxin.financial.ui.my.capital.flow.recharge.withdraw

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 类说明
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class CapitalFlowRWPresenter(v: CapitalFlowRWContract.View) :
        PresenterDataWrapper<String, CapitalFlowRWContract.View>(v),
        CapitalFlowRWContract.Presenter<String> {
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