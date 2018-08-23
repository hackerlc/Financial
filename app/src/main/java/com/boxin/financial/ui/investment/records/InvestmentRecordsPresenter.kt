package com.boxin.financial.ui.investment.records

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.AppConfig.Companion.PAGE_SIZE
import com.boxin.financial.entity.InvestmentRecordInfo
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.InvestmentRecordsListNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 投资记录
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class InvestmentRecordsPresenter(v: InvestmentRecordsContract.View) :
        PresenterDataWrapper<MutableList<InvestmentRecordInfo>, InvestmentRecordsContract.View>(v),
        InvestmentRecordsContract.Presenter<MutableList<InvestmentRecordInfo>> {

    init {
        mData = ArrayList()
    }

    var page = 1

    val mNm = InvestmentRecordsListNM()

    override fun fetch() {
        mView.get()?.onDialog(true)
        mNm.getData(page, PAGE_SIZE)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({
                    if (it.items != null) {
                        processData(it.items!!)
                    } else {
                        mView.get()?.onDialog(false)
                        mView.get()?.updateUI()
                    }
                },
                        { errorData(it) })
    }

    override fun processData(d: MutableList<InvestmentRecordInfo>) {
        super.processData(d)
        mView.get()?.onDialog(false)
        if (page == 1) {
            mData.clear()
        }
        mData.addAll(d)
        mView.get()?.updateUI()
        if (d.size < PAGE_SIZE) {
            mView.get()?.closeMore()
        }
    }

    override fun refreshData() {
        page = 1
        mView.get()?.showMore()
        fetch()
    }

    override fun fetchMore() {
        page++
        fetch()
    }

    override fun getData(): MutableList<InvestmentRecordInfo> {
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