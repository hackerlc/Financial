package com.boxin.financial.ui.password.pay

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 支付密码修改
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class PasswordPayPresenter(v: PasswordPayContract.View) :
        PresenterDataWrapper<String, PasswordPayContract.View>(v),
        PasswordPayContract.Presenter<String> {
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