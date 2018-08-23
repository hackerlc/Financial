package com.boxin.financial.ui.web

import com.boxin.financial.base.contract.BaseContract
import com.boxin.financial.entity.UpApp

/**
 * 查看网页
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
interface WebContract {
    interface View : BaseContract.BaseView{
        fun upDataApp(data: UpApp)
    }

    interface Presenter<T> : BaseContract.BasePresenter<T>{
        fun upDataApp()
    }
}