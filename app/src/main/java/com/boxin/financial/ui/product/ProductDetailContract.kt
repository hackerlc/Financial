package com.boxin.financial.ui.product

import com.boxin.financial.base.contract.BaseContract

/**
 * 产品详情
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface ProductDetailContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>
}