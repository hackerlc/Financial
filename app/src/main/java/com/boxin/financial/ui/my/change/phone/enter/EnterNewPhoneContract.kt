package com.boxin.financial.ui.my.change.phone.enter

import com.boxin.financial.base.contract.BaseContract

/**
 * 确认新手机号
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface EnterNewPhoneContract {
    interface View : BaseContract.BaseView{
        fun ChangeSuccess()
    }

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetchCode(tel: String)
        fun onChangePhone(tel: String,code: String)
    }
}