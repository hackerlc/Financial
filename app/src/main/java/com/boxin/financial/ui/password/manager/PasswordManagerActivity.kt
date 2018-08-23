package com.boxin.financial.ui.password.manager

import android.content.Context
import android.content.Intent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.password.ChangePasswordActivity
import com.boxin.financial.ui.password.pay.PasswordPayActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_password_manager.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 密码管理
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
@Deprecated("页面修改没有用到了")
class PasswordManagerActivity :
        BasePActivity<PasswordManagerContract.Presenter<String>>(R.layout.activity_password_manager),
        PasswordManagerContract.View {

    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context,PasswordManagerActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = PasswordManagerPresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"密码管理",iv_left_img)

        onClickBind(this,iv_left_img, rl_login_pwd, rl_pay_pwd)
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {toEnter(true)}
            R.id.rl_login_pwd -> {ChangePasswordActivity.startAct(this,0)}
            R.id.rl_pay_pwd -> {PasswordPayActivity.startAct(this)}
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