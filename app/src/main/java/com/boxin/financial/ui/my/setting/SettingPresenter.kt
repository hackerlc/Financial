package com.boxin.financial.ui.my.setting

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper

/**
 * 设置
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class SettingPresenter(v: SettingContract.View) :
        PresenterDataWrapper<String, SettingContract.View>(v),
        SettingContract.Presenter<String> {
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