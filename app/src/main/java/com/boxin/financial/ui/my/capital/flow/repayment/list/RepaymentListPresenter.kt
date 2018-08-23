package com.boxin.financial.ui.my.capital.flow.repayment.list

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.BaseList
import com.boxin.financial.entity.RepaymentGroup
import com.boxin.financial.entity.RepaymentInfo
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.RepaymentNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 回款流水列表
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RepaymentListPresenter(val type: Int, v: RepaymentListContract.View) :
        PresenterDataWrapper<MutableList<RepaymentGroup>, RepaymentListContract.View>(v),
        RepaymentListContract.Presenter<MutableList<RepaymentGroup>> {

    init {
        mData = ArrayList()
    }

    var mPage = 1

    val mNM = RepaymentNM()

    override fun fetch() {
        mNM.fetchRepaymentList(type, mPage, AppConfig.PAGE_SIZE)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) })
    }

    override fun fetchMore() {

    }

    fun processData(d: BaseList<RepaymentGroup>) {
        mView.get()?.onDialog(false)
        if (d.items != null){
            if (mPage == 1) {
                mData.clear()
            }
            mData.addAll(d.items!!)
            mView.get()?.updateUI()
            mPage ++
        }
    }

    override fun refreshData() {
        mPage = 1
        fetch()
    }

    override fun getData(): MutableList<RepaymentGroup> {
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