package com.boxin.financial.ui.my.setting

import com.boxin.financial.base.contract.BaseContract

/**
 * 设置
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface SettingContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}