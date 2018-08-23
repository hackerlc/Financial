package com.boxin.financial.ui.home

import com.boxin.financial.base.contract.BaseContract
import com.boxin.financial.entity.Banner

/**
 * 首页
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface HomeContract {
    interface View : BaseContract.BaseView

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun getBanner(): MutableList<Banner>
        fun getNews(): ArrayList<String>
    }
}