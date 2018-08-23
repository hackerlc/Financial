package com.boxin.financial.ui.password.manager

import com.boxin.financial.base.contract.BaseContract

/**
 * 密码管理
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface PasswordManagerContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}