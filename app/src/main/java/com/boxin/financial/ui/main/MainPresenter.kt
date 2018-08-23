package com.boxin.financial.ui.main

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 主要承载页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class MainPresenter(v: MainContract.View) :
        PresenterDataWrapper<String, MainContract.View>(v),
        MainContract.Presenter<String> {
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