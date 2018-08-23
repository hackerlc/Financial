package com.boxin.financial.ui.financial.main

import com.boxin.financial.base.contract.BaseContract

/**
 * 标的
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface FinancialContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}