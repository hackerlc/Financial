package com.boxin.financial.ui.my.auth

import com.boxin.financial.base.contract.BaseContract

/**
 * 实名认证
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface AuthenticationContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}