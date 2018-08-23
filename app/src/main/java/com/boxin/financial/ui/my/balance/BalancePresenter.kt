package com.boxin.financial.ui.my.balance

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 我的余额
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class BalancePresenter(v: BalanceContract.View) :
        PresenterDataWrapper<String, BalanceContract.View>(v),
        BalanceContract.Presenter<String> {
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