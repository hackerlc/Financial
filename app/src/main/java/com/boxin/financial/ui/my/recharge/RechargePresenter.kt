package com.boxin.financial.ui.my.recharge

import com.boxin.financial.R.id.money
import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.AccountOrder
import com.boxin.financial.entity.UserBank
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.UserNM
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 充值页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RechargePresenter(v: RechargeContract.View) :
        PresenterDataWrapper<AccountOrder, RechargeContract.View>(v),
        RechargeContract.Presenter<AccountOrder> {

    val mNM = UserNM()

    /**
     * 绑定银行卡
     */
    override fun fetch() {
        mView.get()?.onDialog(true)
        mNM.bindingBankCard(AppConfig.I_USER?.id!!)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processBankData(it) },
                        { errorData(it) },
                        {mView.get()?.onDialog(false)})
    }

    private fun processBankData(d: String){
        mView.get()?.onDialog(false)
        mView.get()?.showBindingBank(d)
    }

    override fun fetchBankCard() {
        mView.get()?.onDialog(true)
        mNM.fetchBankCard(AppConfig.I_USER?.id!!)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processBankInfoData(it) },
                        { errorData(it) },
                        {mView.get()?.onDialog(false)})
    }

    private fun processBankInfoData(d: MutableList<UserBank>){
        mView.get()?.onDialog(false)
        if (d.size > 0) {
            AppConfig.I_ACCOUNT?.bankInfos?.clear()
            AppConfig.I_ACCOUNT?.bankInfos?.addAll(d)
            mView.get()?.updateUI()
        }
    }

    /**
     * 充值
     */
    override fun fetch(money: String) {
        mView.get()?.onDialog(true)
        mNM.putAddAccountOrder(AppConfig.I_ACCOUNT?.id!!, money, 0)
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