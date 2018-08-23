package com.boxin.financial.ui.guide

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 用户引导
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class GuidePresenter(v: GuideContract.View) :
        PresenterDataWrapper<String, GuideContract.View>(v),
        GuideContract.Presenter<String> {
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