package com.boxin.financial.ui.my.capital.flow.info

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.BaseList
import com.boxin.financial.entity.CapitalFlow
import com.boxin.financial.entity.CapitalFlowGroup
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.CapitalFlowInfoNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 资金流水
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class CapitalFlowInfoPresenter(v: CapitalFlowInfoContract.View) :
        PresenterDataWrapper<MutableList<CapitalFlowGroup>, CapitalFlowInfoContract.View>(v),
        CapitalFlowInfoContract.Presenter<MutableList<CapitalFlowGroup>> {
    init {
        mData = ArrayList()
    }

    var mPage = 1

    val mNM =CapitalFlowInfoNM()

    override fun fetch() {
        mNM.getData(mPage,AppConfig.PAGE_SIZE)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) },
                        { mView.get()?.onDialog(false) })

        mView.get()?.updateUI()
    }

    fun processData(d: BaseList<CapitalFlowGroup>) {
        if (d.items != null && d.items!!.size > 0) {
            if (mPage == 1) {
                mData.clear()
            } else {
                if (mData[mData.size-1].date!!.equals(d.items!![0].date!!)){
                    mData[mData.size-1].capitalFlow.addAll(d.items!![0].capitalFlow)
                    d.items!!.removeAt(0)
                }
            }
            mData.addAll(d.items!!)
            mView.get()?.updateUI()
            mPage ++
        }
    }

    override fun fetchMore() {
        fetch()
        mView.get()?.updateUI()
    }

    override fun refreshData() {
        mPage = 1
        fetch()
    }

    override fun getData(): MutableList<CapitalFlowGroup> {
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