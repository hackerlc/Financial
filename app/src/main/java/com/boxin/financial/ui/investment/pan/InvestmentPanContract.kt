package com.boxin.financial.ui.investment.pan

import com.boxin.financial.base.contract.BaseContract

/**
 * 回款计划
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface InvestmentPanContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}