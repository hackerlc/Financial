package com.boxin.financial.ui.register

import com.boxin.financial.base.contract.BaseContract

/**
 * 注册
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface RegisterContract {
    interface View : BaseContract.BaseView{
        fun toLogin()
        fun timerStart()
    }

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        /**
         * 获取验证码
         * @param phone 电话号码
         * @param type 类型
         */
        fun fetchCode(phone: String,type: String)

        fun onRegister(phone: String,password: String,code: String)

    }
}