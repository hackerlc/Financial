package com.boxin.financial.ui.password.fragment

import com.boxin.financial.base.contract.presenter.PresenterDataWrapper
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.User
import com.boxin.financial.net.helper.SchedulersDataHelper
import com.boxin.financial.net.helper.ThrowableErrorCode
import com.boxin.financial.net.model.UserNM
import com.boxin.financial.utils.SPUtil
import gear.yc.com.gearlibrary.rxjava.helper.RxSchedulersHelper

/**
 * 修改密码公用页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ChangePasswordPresenter(v: ChangePasswordContract.View) :
        PresenterDataWrapper<String, ChangePasswordContract.View>(v),
        ChangePasswordContract.Presenter<String> {
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

    override fun onChangePassword(phone: String, password: String, code: String,flag: String) {
        mView.get()?.onDialog(true)
        mNM.onChangePassword(phone,password,code,"",flag)
                .compose(mView.get()!!.getLifecycleDestroy())
                .compose(RxSchedulersHelper.io_main())
                .compose(SchedulersDataHelper.handleResult())
                .subscribe({ processPassword(it) },
                        { errorData(it) })
    }

    override fun refreshData() {
    }

    private fun processPassword(user: String){
        mView.get()?.onDialog(false)
        AppConfig.PSD_PASSWORD_CODE = 0
        AppConfig.IS_LOGIN = false
        SPUtil.setLogin(AppConfig.IS_LOGIN)
//        AppConfig.I_USER = user
//        SPUtil.saveObj(user, SPUtil.USER_PSD)
        mView.get()?.showToast("修改成功")
        mView.get()?.updateUI()
    }

    override fun getData(): String {
        return mData
    }

    override fun errorData(error: Throwable) {
        super.errorData(error)
        mView.get()?.onDialog(false)
        if (error is ThrowableErrorCode){
            AppConfig.PSD_PASSWORD_CODE ++
            mView.get()?.onError(error)
        }
        mView.get()?.showToast(error.message!!)
    }

    override fun close() {
        mView.clear()
    }
}