package com.boxin.financial.ui.financial.detail

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.Financial
import com.boxin.financial.entity.UserAccount
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.FinancialNM
import com.boxin.financial.net.model.UserNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 标的详情
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialDetailPresenter(v: FinancialDetailContract.View,var id :String) :
        PresenterDataWrapper<Financial, FinancialDetailContract.View>(v),
        FinancialDetailContract.Presenter<Financial> {

    init {
        mData = Financial()
    }
    val mNM = FinancialNM()

    override fun fetch() {
        mView.get()?.onDialog(true)
        mNM.getFinancialDetail(id)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) })
    }

    override fun processData(d: Financial) {
        super.processData(d)
        mView.get()?.onDialog(false)
        mData = d
        mView.get()?.updateUI()
    }

    override fun refreshData() {
    }

    override fun getData(): Financial {
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