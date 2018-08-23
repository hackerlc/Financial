package com.boxin.financial.ui.financial.info

import com.boxin.financial.base.contract.BaseContract

/**
 * 标的详情信息
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface FinancialInfoContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}