package com.boxin.financial.ui.password.manager

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 密码管理
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class PasswordManagerPresenter(v: PasswordManagerContract.View) :
        PresenterDataWrapper<String, PasswordManagerContract.View>(v),
        PasswordManagerContract.Presenter<String> {
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