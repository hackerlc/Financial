package com.boxin.financial.ui.my.recharge

import com.boxin.financial.base.contract.BaseContract

/**
 * 充值页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface RechargeContract {
    interface View : BaseContract.BaseView{
        fun showBindingBank(html: String)
        fun showHtml(html: String)
    }

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetch(money: String)
        fun fetchBankCard()
    }
}