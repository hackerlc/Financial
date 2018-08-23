package com.boxin.financial.ui.my.auth

import android.content.Context
import android.content.Intent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.AppConfig
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.utils.CommonUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 实名认证
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class AuthenticationActivity :
        BasePActivity<AuthenticationContract.Presenter<String>>(R.layout.activity_auth),
        AuthenticationContract.View {
    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context,AuthenticationActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = AuthenticationPresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"实名认证",iv_left_img)

        tv_name.text = AppConfig.I_ACCOUNT!!.realName
        tv_phone.text = CommonUtils.changeStrTrip(AppConfig.I_ACCOUNT?.phone)
        tv_id_card.text = CommonUtils.changeStrTrip(AppConfig.I_ACCOUNT!!.idCard,1)
        tv_pay_id.text = AppConfig.I_ACCOUNT!!.thirdAccount
        tv_bank_name.text = AppConfig.I_ACCOUNT!!.bankInfo()!!.name
        tv_bank_card.text = CommonUtils.changeStrTrip(AppConfig.I_ACCOUNT!!.bankInfo()!!.cardId,2)

        onClickBind(this,iv_left_img)
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