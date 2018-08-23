package com.boxin.financial.ui.my.balance

import com.boxin.financial.base.contract.BaseContract

/**
 * 我的余额
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface BalanceContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}