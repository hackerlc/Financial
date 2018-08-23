package com.boxin.financial.ui.my.hosting

import com.boxin.financial.base.contract.BaseContract

/**
 * 托管
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface HostingContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun fetchHosting(name: String,idCard: String)
    }
}