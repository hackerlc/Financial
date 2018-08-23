package com.boxin.financial.ui.my.change.phone.psd

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.UserNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 使用密码方式修改手机号
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ChangePsdPhonePresenter(v: ChangePsdPhoneContract.View) :
        PresenterDataWrapper<String, ChangePsdPhoneContract.View>(v),
        ChangePsdPhoneContract.Presenter<String> {
    val mNM = UserNM()

    override fun onCheckPhone(tel: String, psd: String, code: String) {
        mView.get()?.onDialog(true)
        mNM.onCheckPhone(tel, psd, code)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processSuccess(it) },
                        { errorData(it) })
    }

    private fun processSuccess(d: String) {
        mView.get()?.onDialog(false)
        mView.get()?.onCheckSuccess()
    }

    override fun fetch() {
    }

    override fun refreshData() {
    }

    override fun getData(): String {
        return mData
    }

    override fun errorData(error: Throwable) {
        super.errorData(error)
        mView.get()?.onDialog(false)
        mView.get()?.showToast(error.localizedMessage)
        mView.get()?.onError(error)
    }

    override fun close() {
        mView.clear()
    }
}