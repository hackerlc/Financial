package com.boxin.financial.ui.financial.detail

import com.boxin.financial.base.contract.BaseContract

/**
 * 标的详情
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface FinancialDetailContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}