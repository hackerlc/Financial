package com.boxin.financial.ui.my.capital.flow.repayment.list

import com.boxin.financial.base.contract.BaseContract

/**
 * 回款流水列表
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface RepaymentListContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetchMore()
    }
}