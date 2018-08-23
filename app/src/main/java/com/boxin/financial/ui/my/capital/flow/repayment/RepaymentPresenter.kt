package com.boxin.financial.ui.my.capital.flow.repayment

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 回款流水，包含待回款和已回款
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RepaymentPresenter(v: RepaymentContract.View) :
        PresenterDataWrapper<String, RepaymentContract.View>(v),
        RepaymentContract.Presenter<String> {
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