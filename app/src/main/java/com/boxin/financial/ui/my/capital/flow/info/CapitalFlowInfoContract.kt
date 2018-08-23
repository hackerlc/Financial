package com.boxin.financial.ui.my.capital.flow.info

import com.boxin.financial.base.contract.BaseContract

/**
 * 资金流水
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface CapitalFlowInfoContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetchMore()
    }
}