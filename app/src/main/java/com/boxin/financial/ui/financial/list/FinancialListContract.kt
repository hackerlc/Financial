package com.boxin.financial.ui.financial.list

import com.boxin.financial.base.contract.BaseContract

/**
 * 投资列表
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface FinancialListContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetchMore()
    }
}