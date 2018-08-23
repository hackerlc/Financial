package com.boxin.financial.ui.my.capital.flow.repayment

import com.boxin.financial.base.contract.BaseContract

/**
 * 回款流水，包含待回款和已回款
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface RepaymentContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}