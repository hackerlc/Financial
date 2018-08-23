package com.boxin.financial.ui.password

import com.boxin.financial.base.contract.BaseContract

/**
 * 密码修改
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface ChangePasswordContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}