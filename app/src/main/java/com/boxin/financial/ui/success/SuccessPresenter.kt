package com.boxin.financial.ui.success

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 各种成功页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class SuccessPresenter(v: SuccessContract.View) :
        PresenterDataWrapper<String, SuccessContract.View>(v),
        SuccessContract.Presenter<String> {
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