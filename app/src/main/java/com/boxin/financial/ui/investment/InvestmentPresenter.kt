package com.boxin.financial.ui.investment

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.entity.InvestmentRecordInfo
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.InvestmentRecordsListNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 投资详情
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class InvestmentPresenter(val id: String, v: InvestmentContract.View) :
        PresenterDataWrapper<InvestmentRecordInfo, InvestmentContract.View>(v),
        InvestmentContract.Presenter<InvestmentRecordInfo> {

    private val mNm = InvestmentRecordsListNM()

    override fun fetch() {
        mView.get()?.onDialog(true)
        mNm.getInvestmentRecords(id)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) })
    }

    override fun processData(d: InvestmentRecordInfo) {
        super.processData(d)
        mView.get()?.onDialog(false)
        mData = d
        mView.get()?.updateUI()
    }

    override fun refreshData() {
    }

    override fun getData(): InvestmentRecordInfo {
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