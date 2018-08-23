package com.boxin.financial.ui.my.main

import com.boxin.financial.base.contract.BaseContract

/**
 * 我的
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface MyContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetchBankCard()
    }
}