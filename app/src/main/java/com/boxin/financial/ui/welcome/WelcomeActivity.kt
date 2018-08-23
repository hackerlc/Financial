package com.boxin.financial.ui.welcome

import android.Manifest
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.User
import com.boxin.financial.ui.guide.GuideActivity
import com.boxin.financial.ui.main.MainActivity
import com.boxin.financial.utils.SPUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


/**
 * 欢迎页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class WelcomeActivity :
        BasePActivity<WelcomeContract.Presenter<User>>(R.layout.activity_welcome),
        WelcomeContract.View {
    private val TIME = 2100L

    override fun attachPresenter() {
        mPresenter = WelcomePresenter(this)
    }

    override fun initUI() {
    }

    override fun initData() {
        //显示权限管理
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe{
                    it ->
                    if (it){
                        Observable.timer(TIME, TimeUnit.MILLISECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .compose(bindToLifecycleDestroy())
                                .subscribe({
                                    if (SPUtil.isFirst(this)) {
                                        GuideActivity.startAct(this)
                                        finish()
                                    } else {
                                        //判断是否登录
                                        AppConfig.IS_LOGIN = SPUtil.isLogin()
                                        if (AppConfig.IS_LOGIN){
                                            AppConfig.I_USER = SPUtil.readObj(SPUtil.USER_PSD) as User
                                            //快速登录
                                            mPresenter.fetch()
                                        } else {
                                            MainActivity.startAct(this,0)
                                            finish()
                                        }
                                    }
                                })
                    } else {
                        ToastUtil.getInstance().makeLongToast(this,"需要获取授权才能正常运行")
                    }
                }
    }

    override fun onStop() {
        super.onStop()
        SPUtil.setFirst(this)
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    override fun updateUI() {
        MainActivity.startAct(this,0)
        finish()
    }

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(this, str)
    }

    override fun onError(error: Throwable) {
        MainActivity.startAct(this,0)
        finish()
    }

    override fun onDialog(show: Boolean) {
        if (show) {
            ProgressDialogUtil.getInstance().build(this).show()
        } else {
            ProgressDialogUtil.getInstance().dismiss()
        }
    }

    override fun <R> getLifecycle2(): LifecycleTransformer<R> {
        return bindToLifecycle()
    }

    override fun <R> getLifecycleDestroy(): LifecycleTransformer<R> {
        return bindToLifecycleDestroy()
    }

}