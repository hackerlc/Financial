package com.boxin.financial.ui.pay

import com.boxin.financial.base.contract.BaseContract

/**
 * 支付页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface FinancialPayContract {
    interface View : BaseContract.BaseView{
        fun showHtml(str: String)
    }

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetch(lendMoneryVo: String,loanIdVo: String)
    }
}