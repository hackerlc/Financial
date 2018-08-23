package com.boxin.financial.ui.my.change.phone.code

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.helper.ThrowableErrorCode
import com.boxin.financial.net.model.UserNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 修改原手机号
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ChangeOldPhonePresenter(v: ChangeOldPhoneContract.View) :
        PresenterDataWrapper<String, ChangeOldPhoneContract.View>(v),
        ChangeOldPhoneContract.Presenter<String> {
    val mNM = UserNM()

    override fun fetchCode(tel: String) {
        mView.get()?.onDialog(true)
        mNM.fetchCode(tel, APIConfig.VERIFY_CODE_CHANGE_PHONE_CHECK)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) })
    }

    override fun processData(d: String) {
        mView.get()?.onDialog(false)
    }

    override fun onChange(tel: String, psd: String, code: String) {
        mView.get()?.onDialog(true)
        mNM.onCheckPhone(tel, psd, code)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processSuccess(it) },
                        { errorData(it) })
    }

    private fun processSuccess(d: String){
        mView.get()?.onDialog(false)
        mView.get()?.checkSuccess()
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
                AppConfig.PSD_CHANGE_OLD++
                mView.get()?.onError(error)
            }
        }
        mView.get()?.showToast(error.message!!)
    }

    override fun close() {
        mView.clear()
    }
}