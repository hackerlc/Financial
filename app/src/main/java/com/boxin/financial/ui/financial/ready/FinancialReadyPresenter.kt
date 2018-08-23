package com.boxin.financial.ui.financial.ready

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.UserAccount
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.FinancialNM
import com.boxin.financial.net.model.UserNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 立即投资页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialReadyPresenter(v: FinancialReadyContract.View) :
        PresenterDataWrapper<String, FinancialReadyContract.View>(v),
        FinancialReadyContract.Presenter<String> {

    val mNM = FinancialNM()
    val mUserNM = UserNM()
    override fun fetch(){}

    override fun fetch(lendMoneryVo: String,loanIdVo: String) {
        mView.get()?.onDialog(true)
        mNM.fetchAddLend(lendMoneryVo, loanIdVo)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) })
    }

    override fun processData(d: String) {
        super.processData(d)
        mView.get()?.onDialog(false)
        mView.get()?.updateUI()
    }

    override fun fetchUserInfo() {
        mUserNM.fetchUserAccount(AppConfig.I_USER?.id!!)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processUserData(it) },
                        { errorData(it) })
    }

    private fun processUserData(d: UserAccount){
        AppConfig.I_ACCOUNT = d
        mView.get()?.updateUI()
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