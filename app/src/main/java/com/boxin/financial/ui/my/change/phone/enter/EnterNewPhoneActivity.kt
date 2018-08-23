package com.boxin.financial.ui.my.change.phone.enter

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.AppConfig
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.login.LoginActivity
import com.boxin.financial.ui.web.WebH5Activity
import com.boxin.financial.utils.CommonUtils
import com.boxin.financial.utils.SPUtil
import com.boxin.financial.view.VerificationCountDownTimer
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_enter_new_phone.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 确认新手机号
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class EnterNewPhoneActivity :
        BasePActivity<EnterNewPhoneContract.Presenter<String>>(R.layout.activity_enter_new_phone),
        EnterNewPhoneContract.View {

    lateinit var downTimer : VerificationCountDownTimer

    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context,EnterNewPhoneActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = EnterNewPhonePresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"确认新手机号",iv_left_img)

        et_tel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_ok.isEnabled = s?.length == 11 && et_code.text.toString().length >= 4
                tv_code.isEnabled = s?.length == 11
            }
        })
        et_code.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                btn_ok.isEnabled = s.length >= 4 && et_tel.text.length == 11
            }
        })
        if (AppConfig.PSD_CHANGE_NEW >= 2){
            ll_verify.visibility = View.VISIBLE
            tv_verify.text = CommonUtils.fetchCode()
        } else {
            ll_verify.visibility = View.GONE
        }

        onClickBind(this,iv_left_img,btn_ok,tv_code,tv_verify)
    }

    override fun initData() {
        if (!VerificationCountDownTimer.FLAG_FIRST_IN &&
                VerificationCountDownTimer.curMillis+60000L > System.currentTimeMillis()){
            setCodeTimer(VerificationCountDownTimer.curMillis+60000L - System.currentTimeMillis())
            downTimer.timerStart(false,4)
        } else {
            setCodeTimer(60000L)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        downTimer.cancel()
        mPresenter.close()
    }

    override fun ChangeSuccess() {
        AppConfig.IS_LOGIN = false
        SPUtil.setLogin(AppConfig.IS_LOGIN)
        showToast("修改成功")
        //这里是web的写法，如果改成原生这里要屏蔽，不然可能会出现null错误
        WebH5Activity.isLoad = true
        LoginActivity.startAct(this)
        finish()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_code -> {
                val tel1 = et_tel.text.toString()
                if (!CommonUtils.matchPhone(tel1)) {
                    showToast("手机号格式不正确")
                    return
                }
                if (tel1 == AppConfig.I_USER?.phone){
                    showToast("请输入新手机号")
                    return
                }
                //验证码对比
                if (AppConfig.PSD_CHANGE_NEW >= 2 && "" == et_verify.text.toString()) {
                    showToast("图形验证码不能为空")
                    return
                }
                if (AppConfig.PSD_CHANGE_NEW >= 2 && !tv_verify.text.equals(et_verify.text.toString())){
                    showToast("图形验证码错误")
                    return
                } else {
                    //验证码变更
                    tv_verify.text = CommonUtils.fetchCode()
                }
                mPresenter.fetchCode(tel1)
                downTimer.timerStart(true,0)
            }
            R.id.btn_ok -> {
                val tel1 = et_tel.text.toString()
                if (!CommonUtils.matchPhone(tel1)) {
                    showToast("手机号格式不正确")
                    return
                }
                if (tel1 == AppConfig.I_USER?.phone){
                    showToast("请输入新手机号")
                    return
                }
                //验证码对比
                if (AppConfig.PSD_CHANGE_NEW >= 2 && "" == et_verify.text.toString()) {
                    showToast("图形验证码不能为空")
                    return
                }
                if (AppConfig.PSD_CHANGE_NEW >= 2 && !tv_verify.text.equals(et_verify.text.toString())){
                    showToast("图形验证码错误")
                    return
                }
                //验证码变更
                tv_verify.text = CommonUtils.fetchCode()
                mPresenter.onChangePhone(tel1,et_code.text.toString())
            }
            R.id.tv_verify -> {
                //验证码变更
                tv_verify.text = CommonUtils.fetchCode()
            }
            R.id.iv_left_img -> {
                toExit(true)
            }
        }
    }

    override fun updateUI() {
    }

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(this, str)
    }

    override fun onError(error: Throwable) {
        if (AppConfig.PSD_CHANGE_NEW >= 2){
            ll_verify.visibility = View.VISIBLE
        } else {
            ll_verify.visibility = View.GONE
        }
    }

    private fun setCodeTimer(countDownTime: Long){
        downTimer = object : VerificationCountDownTimer(countDownTime, 1000) {
            override fun onTick(l: Long) {
                if (tv_code != null){
                    if (tv_code.isClickable) {
                        tv_code.isClickable = false
                    }
                    tv_code.text = "${l / 1000}s"
                }
            }

            override fun onFinish() {
                if (tv_code != null) {
                    tv_code.isClickable = true
                    tv_code.text = "重新发送"
                }
                if (countDownTime != 60000L){
                    setCodeTimer(60000L)
                }
            }
        }
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