package com.boxin.financial.ui.password

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 密码修改
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ChangePasswordPresenter(v: ChangePasswordContract.View) :
        PresenterDataWrapper<String, ChangePasswordContract.View>(v),
        ChangePasswordContract.Presenter<String> {
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