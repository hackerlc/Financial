package com.boxin.financial.ui.welcome

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.User
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.model.UserNM
import com.boxin.financial.utils.SPUtil
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 欢迎页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class WelcomePresenter(v: WelcomeContract.View) :
        PresenterDataWrapper<User, WelcomeContract.View>(v),
        WelcomeContract.Presenter<User> {

    val mNM = UserNM()
    override fun fetch() {
        if (AppConfig.I_USER?.phone != null) {
            mNM.onLogin(AppConfig.I_USER?.phone!!, "", "")
                    .compose(mView.get()?.getLifecycleDestroy())
                    .compose(RxSchedulersHelper.io_main())
                    .compose(SchedulersDataHelper.handleResult())
                    .subscribe({ processData(it) },
                            { errorData(it) })
        } else {
            errorData(Throwable("手机号未能找到"))
        }
    }

    override fun processData(d: User) {
        super.processData(d)
        //登录成功保存用户信息
        AppConfig.I_USER = d
        SPUtil.saveObj(d, SPUtil.USER_PSD)
        mView.get()?.updateUI()
    }

    override fun refreshData() {
    }

    override fun getData(): User {
        return mData
    }

    override fun errorData(error: Throwable) {
        super.errorData(error)
        AppConfig.IS_LOGIN = false
        SPUtil.setLogin(AppConfig.IS_LOGIN)
        mView.get()?.onError(error)
    }

    override fun close() {
        mView.clear()
    }
}