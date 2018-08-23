package com.boxin.financial.ui.discount

import com.boxin.financial.base.contract.BaseContract

/**
 * 折扣
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface DiscountContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}