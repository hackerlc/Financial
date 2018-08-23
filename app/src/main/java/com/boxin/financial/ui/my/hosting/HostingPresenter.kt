package com.boxin.financial.ui.my.hosting

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.UserNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 托管
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class HostingPresenter(v: HostingContract.View) :
        PresenterDataWrapper<String, HostingContract.View>(v),
        HostingContract.Presenter<String> {
    init {
        mData = ""
    }

    val mNM = UserNM()

    override fun fetch() {

    }

    override fun fetchHosting(name: String, idCard: String) {
        if (AppConfig.I_USER != null && AppConfig.I_USER?.id != null) {
            mView.get()?.onDialog(true)
            mNM.putUserInfoHosting(AppConfig.I_USER?.id!!, name, idCard,APIConfig.BASE_RETURN_URL)
                    .compose(mView.get()!!.getLifecycleDestroy())
                    .compose(RxSchedulersHelper.io_main())
                    .compose(SchedulersDataHelper.handleResult())
                    .subscribe({ processData(it) },
                            { errorData(it) })
        } else {
            mView.get()?.showToast("用户手机号未找到")
        }
    }

    override fun processData(d: String) {
        mView.get()?.onDialog(false)
        mData = d
        mView.get()?.updateUI()
    }

    override fun refreshData() {
    }

    override fun getData(): String {
        return mData
    }

    override fun errorData(error: Throwable) {
        super.errorData(error)
        mView.get()?.onDialog(false)
        mView.get()?.onError(error)
    }

    override fun close() {
        mView.clear()
    }
}