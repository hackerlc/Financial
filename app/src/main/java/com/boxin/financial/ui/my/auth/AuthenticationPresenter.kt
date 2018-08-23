package com.boxin.financial.ui.my.auth

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 实名认证
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class AuthenticationPresenter(v: AuthenticationContract.View) :
        PresenterDataWrapper<String, AuthenticationContract.View>(v),
        AuthenticationContract.Presenter<String> {
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