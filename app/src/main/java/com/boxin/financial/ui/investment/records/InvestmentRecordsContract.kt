package com.boxin.financial.ui.investment.records

import com.boxin.financial.base.contract.BaseContract

/**
 * 投资记录
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface InvestmentRecordsContract {
    interface View : BaseContract.BaseView{
        fun closeMore()
        fun showMore()
    }

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetchMore()
    }
}