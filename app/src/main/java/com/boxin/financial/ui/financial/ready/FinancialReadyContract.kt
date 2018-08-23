package com.boxin.financial.ui.financial.ready

import com.boxin.financial.base.contract.BaseContract

/**
 * 立即投资页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface FinancialReadyContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetch(lendMoneryVo: String,loanIdVo: String)
        fun fetchUserInfo()
    }
}