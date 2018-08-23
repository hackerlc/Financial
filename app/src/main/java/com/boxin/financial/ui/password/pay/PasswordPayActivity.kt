package com.boxin.financial.ui.password.pay

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.Gravity
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.view.popwindow.SelectPopupWindow
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_password_pay.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 支付密码修改
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class PasswordPayActivity :
        BasePActivity<PasswordPayContract.Presenter<String>>(R.layout.activity_password_pay),
        PasswordPayContract.View,SelectPopupWindow.OnPopWindowClickListener {
    lateinit var menuWindow: SelectPopupWindow
    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context,PasswordPayActivity::class.java))
        }
    }
    override fun attachPresenter() {
        mPresenter = PasswordPayPresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"支付密码修改",iv_left_img)

        onClickBind(this,iv_left_img,btn_ok)
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {toExit(true)}
            R.id.btn_ok -> {
                inoutPsw()
            }
        }
    }

    //打开输入密码的对话框
    private fun inoutPsw() {
        menuWindow = SelectPopupWindow(this, this)
        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        val winHeight = window.decorView.height
        menuWindow.showAtLocation(window.decorView, Gravity.BOTTOM, 0, winHeight - rect.bottom)
    }

    override fun onPopWindowClickListener(psw: String?, complete: Boolean) {
        ToastUtil.getInstance().makeShortToast(this, "您输入的密码是$psw")
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