package com.boxin.financial.ui.financial.more

import com.boxin.financial.base.contract.BaseContract

/**
 * 标的详情更多信息
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface FinancialMoreContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}