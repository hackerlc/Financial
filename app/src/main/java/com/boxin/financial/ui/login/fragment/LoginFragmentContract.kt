package com.boxin.financial.ui.login.fragment

import com.boxin.financial.base.contract.BaseContract

/**
 * 快速登录
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface LoginFragmentContract {
    interface View : BaseContract.BaseView{
        fun toRegister()
    }

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetchCode(phone :String)
        fun onLogin(phone: String, password: String, code: String)
    }
}