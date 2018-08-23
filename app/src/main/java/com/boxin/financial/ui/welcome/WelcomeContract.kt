package com.boxin.financial.ui.welcome

import com.boxin.financial.base.contract.BaseContract

/**
 * 欢迎页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface WelcomeContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}