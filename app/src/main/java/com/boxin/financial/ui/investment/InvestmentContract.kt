package com.boxin.financial.ui.investment

import com.boxin.financial.base.contract.BaseContract

/**
 * 投资详情
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface InvestmentContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}