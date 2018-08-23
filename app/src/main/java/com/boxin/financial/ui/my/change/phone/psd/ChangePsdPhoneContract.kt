package com.boxin.financial.ui.my.change.phone.psd

import com.boxin.financial.base.contract.BaseContract

/**
 * 使用密码方式修改手机号
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface ChangePsdPhoneContract {
    interface View : BaseContract.BaseView{
        fun onCheckSuccess()
    }

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun onCheckPhone(tel: String, psd: String, code: String)
    }
}