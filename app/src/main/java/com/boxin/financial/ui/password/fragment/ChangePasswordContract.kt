package com.boxin.financial.ui.password.fragment

import com.boxin.financial.base.contract.BaseContract

/**
 * 修改密码公用页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface ChangePasswordContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        /**
         * 获取验证码
         * @param phone 电话号码
         * @param type 类型
         */
        fun fetchCode(phone: String,type: String)

        fun onChangePassword(phone: String,password: String,code: String,flag: String)
    }
}