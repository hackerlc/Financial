package com.boxin.financial.ui.my.main

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.AppConfig
import com.boxin.financial.config.AppConfig.Companion.I_ACCOUNT
import com.boxin.financial.config.AppConfig.Companion.I_USER
import com.boxin.financial.entity.UserAccount
import com.boxin.financial.entity.UserBank
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.UserNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 我的
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
open class MyPresenter(v: MyContract.View) :
        PresenterDataWrapper<UserAccount, MyContract.View>(v),
        MyContract.Presenter<UserAccount> {

    val mNM = UserNM()
    override fun fetch() {
        if (!I_USER?.id.isNullOrEmpty()) {
            mView.get()?.onDialog(true)
            mNM.fetchUserAccount(I_USER?.id!!)
                    .compose(mView.get()!!.getLifecycleDestroy())
                    .compose(RxSchedulersHelper.io_main())
                    .compose(SchedulersDataHelper.handleResult())
                    .subscribe({ processData(it) },
                            { errorData(it) })
        } else {
            errorData(Throwable("未登录"))
        }
    }

    override fun processData(d: UserAccount) {
        super.processData(d)
        mView.get()?.onDialog(false)
        I_ACCOUNT = d
        mView.get()?.updateUI()
    }

    override fun fetchBankCard() {
        if (!I_USER?.id.isNullOrEmpty()) {
            mNM.fetchBankCard(AppConfig.I_USER?.id!!)
                    .compose(mView.get()!!.getLifecycleDestroy())
                    .compose(RxSchedulersHelper.io_main())
                    .compose(SchedulersDataHelper.handleResult())
                    .subscribe({ processBankInfoData(it) },
                            { errorData(it) },
                            { mView.get()?.onDialog(false) })
        }
    }

    private fun processBankInfoData(d: MutableList<UserBank>){
        if (d.size > 0) {
            AppConfig.I_ACCOUNT?.bankInfos?.clear()
            AppConfig.I_ACCOUNT?.bankInfos?.addAll(d)
        }
    }

    override fun refreshData() {
        fetch()
        fetchBankCard()
    }

    override fun getData(): UserAccount {
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