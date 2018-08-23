package com.boxin.financial.ui.my.change.phone.enter

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.helper.ThrowableErrorCode
import com.boxin.financial.net.model.UserNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 确认新手机号
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class EnterNewPhonePresenter(v: EnterNewPhoneContract.View) :
        PresenterDataWrapper<String, EnterNewPhoneContract.View>(v),
        EnterNewPhoneContract.Presenter<String> {

    val mNM = UserNM()

    override fun fetchCode(tel: String) {
        mView.get()?.onDialog(true)
        mNM.fetchCode(tel, APIConfig.VERIFY_CODE_CHANGE_PHONE_OLD)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) })
    }

    override fun processData(d: String) {
        mView.get()?.onDialog(false)
    }

    override fun onChangePhone(tel: String, code: String) {
        mView.get()?.onDialog(true)
        mNM.onChangePhone(tel,code)
                .compose(mView.get()!!.getLifecycleDestroy())
                 .compose(RxSchedulersHelper.io_main())
                 .compose(SchedulersDataHelper.handleResult())
                         .subscribe({ processSuccess(it) },
                                { errorData(it) })
    }

    private fun processSuccess(d: String){
        mView.get()?.onDialog(false)
        mView.get()?.ChangeSuccess()
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
        when (error) {
            is ThrowableErrorCode -> {
                AppConfig.PSD_CHANGE_NEW++
                mView.get()?.onError(error)
            }
        }
        mView.get()?.showToast(error.message!!)
    }

    override fun close() {
        mView.clear()
    }
}