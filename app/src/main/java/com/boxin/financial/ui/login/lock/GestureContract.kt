package com.boxin.financial.ui.login.lock

import com.boxin.financial.base.contract.BaseContract

/**
 * 类说明
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface GestureContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}