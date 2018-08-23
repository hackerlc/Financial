package com.boxin.financial.ui.guide

import com.boxin.financial.base.contract.BaseContract

/**
 * 用户引导
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface GuideContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}