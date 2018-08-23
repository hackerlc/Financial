package com.boxin.financial.ui.my.withdraw

import com.boxin.financial.base.contract.BaseContract

/**
 * 提现
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface WithdrawContract {
    interface View : BaseContract.BaseView{
        fun showHtml(html: String)
    }

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetch(money: String,isFast: Boolean)

    }
}