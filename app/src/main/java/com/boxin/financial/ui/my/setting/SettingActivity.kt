package com.boxin.financial.ui.my.setting

import android.content.Context
import android.content.Intent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.common.dialog.CommonDialog
import com.boxin.financial.ui.login.LoginActivity
import com.boxin.financial.ui.login.lock.GestureActivity
import com.boxin.financial.ui.login.lock.GestureCreateActivity
import com.boxin.financial.ui.my.change.phone.ChangePhoneActivity
import com.boxin.financial.ui.my.hosting.HostingActivity
import com.boxin.financial.ui.password.ChangePasswordActivity
import com.boxin.financial.ui.web.WebActivity
import com.boxin.financial.ui.web.WebH5Activity
import com.boxin.financial.utils.CommonUtils
import com.boxin.financial.utils.SPUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.rxjava.rxbus.RxBus
import gear.yc.com.gearlibrary.rxjava.rxbus.annotation.Subscribe
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 设置
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class SettingActivity :
        BasePActivity<SettingContract.Presenter<String>>(R.layout.activity_setting),
        SettingContract.View {
    companion object {
        fun startAct(context: Context) {
            context.startActivity(Intent(context, SettingActivity::class.java))
        }
    }

    /**
     * 开户dialog
     */
    lateinit var dialog: CommonDialog

    override fun attachPresenter() {
        mPresenter = SettingPresenter(this)
        RxBus.getInstance().register(this)
    }

    override fun initUI() {
        onHeadChange(head_top, tv_title, "设置", iv_left_img)

        tv_version.text = "V${CommonUtils.versionName}"

        sw_touch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (sw_touch.isChecked == AppConfig.I_USER?.signLock){
                return@setOnCheckedChangeListener
            }
            if (sw_touch.isChecked) {
                GestureCreateActivity.startActivity(this)
            } else {
                GestureActivity.startAct(this, "setting")
            }
        }
        onClickBind(this, iv_left_img, rl_psd, btn_exit, rl_about, rl_pingce, rl_phone)
    }

    override fun initData() {
        dialog = CommonDialog(this, "", "为了保障您的资金安全\n在您投资前需要开通 汇付天下托管账户"
                , 0, "稍后开通", "立即开通", object : CommonDialog.onDialogDoubleButtonClickListener {
            override fun onLeftClick() {
                dialog.dismiss()
            }

            override fun onRightClick() {
                dialog.dismiss()
                HostingActivity.startAct(this@SettingActivity)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (!AppConfig.IS_LOGIN) {
            toExit(true)
        } else {
            sw_touch.isChecked = AppConfig.I_USER?.signLock!!
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.getInstance().unRegister(this)
        mPresenter.close()
    }

    @Subscribe(tag = RxBus.TAG_OTHER)
    fun onClose(boolean: Boolean){
        if (boolean) {
            finish()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {
                toExit(true)
            }
            R.id.rl_psd -> {
                ChangePasswordActivity.startAct(this, 0)
                toEnter(false)
            }
            R.id.btn_exit -> {
                AppConfig.IS_LOGIN = false
                SPUtil.setLogin(AppConfig.IS_LOGIN)
                showToast("退出登录")
                //这里是web的写法，如果改成原生这里要屏蔽，不然可能会出现null错误
                WebH5Activity.isLoad = true
                LoginActivity.startAct(this)
                finish()
//                MainActivity.startAct(this,2)
//
//                toExit(true)
            }
            R.id.rl_about -> {
                WebActivity.startAct(this, APIConfig.fetchHtmlUrl(APIConfig.HTML_URL_ABOUT, 0), "")
            }
            R.id.rl_pingce -> {
                if (!AppConfig.I_ACCOUNT!!.open) {
                    dialog.show()
                    return
                }
                WebActivity.startAct(this, APIConfig.fetchHtmlUrl(APIConfig.HTML_URL_REVIEW, 0), "")
            }
            R.id.rl_phone -> {
                ChangePhoneActivity.startAct(this)
            }
        }
    }

    override fun updateUI() {
        TODO("not implemented") //To change FieldMap of created functions use File | Settings | File Templates.
    }

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(this, str)
    }

    override fun onError(error: Throwable) {

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