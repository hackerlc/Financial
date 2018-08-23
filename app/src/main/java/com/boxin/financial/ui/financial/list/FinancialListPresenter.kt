package com.boxin.financial.ui.financial.list

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.entity.Financial
import com.boxin.financial.entity.FinancialList
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.FinancialNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 投资列表
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialListPresenter(v: FinancialListContract.View, var type: Int) :
        PresenterDataWrapper<MutableList<Financial>, FinancialListContract.View>(v),
        FinancialListContract.Presenter<MutableList<Financial>> {
    init {
        mData = ArrayList()
    }

    companion object {
        var num = 0
        var more = 0
    }

    val mNModel = FinancialNM()
    override fun fetch() {
        mNModel.getData(type == 1,1)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processListData(it) },
                        { errorData(it) },
                        { mView.get()?.onDialog(false) })
    }
    /**
     * 募集中数据处理
     */
    private fun processListData(d: MutableList<Financial>) {
        mData.clear()
        mData.addAll(d)
        num = mData.size
        if (type == 0) {
            mData.add(Financial())
        }
        mView.get()?.updateUI()
    }

    override fun fetchMore() {
        mNModel.getData(type == 1,0)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processListMoreData(it) },
                        { errorData(it) },
                        { mView.get()?.onDialog(false) })
    }

    private fun processListMoreData(d: MutableList<Financial>) {
        mData.addAll(d)
        mView.get()?.updateUI()
    }

    override fun refreshData() {
        fetch()
        mView.get()?.updateUI()
    }

    override fun getData(): MutableList<Financial> {
        return mData
    }

    override fun errorData(error: Throwable) {
        super.errorData(error)
        mView.get()?.showToast(error.localizedMessage)
        mView.get()?.onError(error)
    }

    override fun close() {
        mView.clear()
    }
}