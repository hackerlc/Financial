package com.boxin.financial.ui.register

import android.content.Context
import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.config.AppConfig.Companion.IS_LOGIN
import com.boxin.financial.config.AppConfig.Companion.I_USER
import com.boxin.financial.entity.User
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.helper.ThrowableErrorCode
import com.boxin.financial.net.helper.ThrowableErrorUserValid
import com.boxin.financial.net.helper.ThrowableSuccess4DataNull
import com.boxin.financial.net.model.UserNM
import com.boxin.financial.utils.SPUtil
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper
import gear.yc.com.gearlibrary.utils.ToastUtil

/**
 * 注册
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RegisterPresenter(v: RegisterContract.View,val context: Context) :
        PresenterDataWrapper<String, RegisterContract.View>(v),
        RegisterContract.Presenter<String> {
    init {
        mData = ""
    }
    val mNM = UserNM()
    override fun fetch() {
    }

    override fun fetchCode(phone: String,type: String) {
        mView.get()?.onDialog(true)
        mNM.fetchCode(phone,type)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processData(it) },
                        { errorData(it) })

    }

    override fun processData(d: String) {
        mView.get()?.onDialog(false)
    }

    override fun onRegister(phone: String, password: String, code: String) {
        mView.get()?.onDialog(true)
        mNM.onRegister(phone,password,code)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processRegister(it) },
                        { errorData(it) })
    }

    override fun refreshData() {
    }

    private fun processRegister(user: User){
        mView.get()?.onDialog(false)
        AppConfig.PSD_REGISTER_CODE = 0
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
        mView.get()?.showToast("注册成功")
        mView.get()?.updateUI()
    }

    override fun getData(): String {
        return mData
    }

    override fun errorData(error: Throwable) {
        super.errorData(error)
        mView.get()?.onDialog(false)
        if (error.message != null) {
            mView.get()?.showToast(error.message!!)
        }
        if (error is ThrowableErrorUserValid){
            mView.get()?.toLogin()
        }
        if (error is ThrowableErrorCode){
            AppConfig.PSD_REGISTER_CODE ++
            mView.get()?.onError(error)
        }
    }

    override fun close() {
        mView.clear()
    }
}