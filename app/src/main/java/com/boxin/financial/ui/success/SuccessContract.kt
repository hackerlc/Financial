package com.boxin.financial.ui.success

import com.boxin.financial.base.contract.BaseContract

/**
 * 各种成功页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface SuccessContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}