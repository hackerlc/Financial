package com.boxin.financial.ui.login.fragment

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.User
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.helper.ThrowableErrorCode
import com.boxin.financial.net.helper.ThrowableErrorUserPassword
import com.boxin.financial.net.helper.ThrowableErrorUserValid
import com.boxin.financial.net.model.UserNM
import com.boxin.financial.utils.CommonUtils
import com.boxin.financial.utils.SPUtil
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 快速登录
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class LoginFragmentPresenter(v: LoginFragmentContract.View) :
        PresenterDataWrapper<String, LoginFragmentContract.View>(v),
        LoginFragmentContract.Presenter<String> {

    val mNM = UserNM()
    var mPhone = ""
    override fun fetch() {
    }

    override fun fetchCode(phone: String) {
        mNM.fetchCode(phone,APIConfig.VERIFY_CODE_LOGIN)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) })
    }
    override fun processData(d: String) {
        mView.get()?.onDialog(false)
    }
    override fun onLogin(phone: String, password: String, code: String) {
        mView.get()?.onDialog(true)
        mPhone = phone
        mNM.onLogin(phone, password,code)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processLogin(it) },
                        { errorData(it) },
                        { mView.get()?.onDialog(false) })
    }
    private fun processLogin(user: User){
        mView.get()?.onDialog(false)
        AppConfig.PSD_FAST_CODE = 0
        //登录成功保存用户信息
        AppConfig.IS_LOGIN = true
        SPUtil.setLogin(AppConfig.IS_LOGIN)
        if (AppConfig.I_USER == null){
            user.signLock = false
        } else {
            user.signLock = AppConfig.I_USER!!.signLock
        }
        AppConfig.I_USER = user
        SPUtil.saveObj(user,SPUtil.USER_PSD)
        mView.get()?.showToast("登录成功")
        mView.get()?.updateUI()
    }
    override fun refreshData() {
    }

    override fun getData(): String {
        return mData
    }

    override fun errorData(error: Throwable) {
        super.errorData(error)
        mView.get()?.onDialog(false)
        when(error) {
            is ThrowableErrorUserValid,is ThrowableErrorUserValid -> {
                mView.get()?.toRegister()
            }
            is ThrowableErrorCode -> {
                AppConfig.PSD_FAST_CODE ++
                mView.get()?.onError(error)
            }
            is ThrowableErrorUserPassword -> {
                CommonUtils.onPhonePasswordAdd(mPhone)
            }
        }
        mView.get()?.showToast(error.message!!)
    }

    override fun close() {
        mView.clear()
    }
}