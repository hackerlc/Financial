package com.boxin.financial.ui.pay

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.FinancialNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 支付页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialPayPresenter(v: FinancialPayContract.View) :
        PresenterDataWrapper<String, FinancialPayContract.View>(v),
        FinancialPayContract.Presenter<String> {

    val mNM = FinancialNM()

    override fun fetch() {

    }

    override fun fetch(lendMoneryVo: String, loanIdVo: String) {
        mView.get()?.onDialog(true)
        mNM.payFinancialMoney(lendMoneryVo, loanIdVo)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) })
    }

    override fun processData(d: String) {
        super.processData(d)
        mView.get()?.onDialog(false)
        mView.get()?.showHtml(d)
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