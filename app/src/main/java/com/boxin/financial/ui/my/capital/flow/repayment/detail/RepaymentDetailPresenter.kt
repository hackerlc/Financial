package com.boxin.financial.ui.my.capital.flow.repayment.detail

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.entity.RepaymentDetail
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.RepaymentNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 回款详情
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RepaymentDetailPresenter(v: RepaymentDetailContract.View, val id: String) :
        PresenterDataWrapper<RepaymentDetail, RepaymentDetailContract.View>(v),
        RepaymentDetailContract.Presenter<RepaymentDetail> {

    init {
        mData = RepaymentDetail()
    }

    val mNM = RepaymentNM()
    override fun fetch() {
        mNM.fetchRepaymentDetail(id)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) })
    }

    override fun processData(d: RepaymentDetail) {
        super.processData(d)
        mData = d
        mView.get()?.updateUI()
    }

    override fun refreshData() {
    }

    override fun getData(): RepaymentDetail {
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