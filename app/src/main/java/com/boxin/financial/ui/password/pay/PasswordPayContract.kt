package com.boxin.financial.ui.password.pay

import com.boxin.financial.base.contract.BaseContract

/**
 * 支付密码修改
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface PasswordPayContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}