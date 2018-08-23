package com.boxin.financial.ui.my.withdraw

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.AccountOrder
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.UserNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 提现
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class WithdrawPresenter(v: WithdrawContract.View) :
        PresenterDataWrapper<AccountOrder, WithdrawContract.View>(v),
        WithdrawContract.Presenter<AccountOrder> {

    val mNM = UserNM()

    override fun fetch() {
    }

    override fun fetch(money: String,isFast: Boolean) {
        mView.get()?.onDialog(true)
        mNM.putAddAccountOrder(AppConfig.I_ACCOUNT?.id!!, money, if (isFast) 2 else 1)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processNewData(it) },
                        { errorData(it) },
                        {mView.get()?.onDialog(false)})

    }

    fun processNewData(d: String) {
        mView.get()?.showHtml(d)
    }

    override fun refreshData() {
    }

    override fun getData(): AccountOrder {
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