package com.boxin.financial.ui.my.change.phone

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 修改手机号
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ChangePhonePresenter(v: ChangePhoneContract.View) :
        PresenterDataWrapper<String, ChangePhoneContract.View>(v),
        ChangePhoneContract.Presenter<String> {
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