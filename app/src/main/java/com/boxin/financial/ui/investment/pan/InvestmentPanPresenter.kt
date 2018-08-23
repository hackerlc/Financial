package com.boxin.financial.ui.investment.pan

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.entity.InvestmentPanInfo
import com.boxin.financial.net.model.InvestmentPanListNM

/**
 * 回款计划
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class InvestmentPanPresenter(v: InvestmentPanContract.View) :
        PresenterDataWrapper<MutableList<InvestmentPanInfo>, InvestmentPanContract.View>(v),
        InvestmentPanContract.Presenter<MutableList<InvestmentPanInfo>> {
    init {
        mData = ArrayList()
    }
    val mNM = InvestmentPanListNM()
    override fun fetch() {
        mNM.getTestData(mData)

        refreshData()
    }

    override fun refreshData() {
        mView.get()?.updateUI()
    }

    override fun getData(): MutableList<InvestmentPanInfo> {
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