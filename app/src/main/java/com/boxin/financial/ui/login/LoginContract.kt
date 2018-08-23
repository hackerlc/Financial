package com.boxin.financial.ui.login

import com.boxin.financial.base.contract.BaseContract

/**
 * 登录
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface LoginContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}