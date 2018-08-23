package com.boxin.financial.ui.my.capital.flow.recharge.withdraw.list

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 充值提现列表
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RWListPresenter(v: RWListContract.View) :
        PresenterDataWrapper<String, RWListContract.View>(v),
        RWListContract.Presenter<String> {
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