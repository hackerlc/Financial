package com.boxin.financial.ui.login.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.config.AppConfig
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.login.lock.GestureCreateActivity
import com.boxin.financial.ui.password.ChangePasswordActivity
import com.boxin.financial.ui.register.RegisterActivity
import com.boxin.financial.ui.web.WebH5Activity
import com.boxin.financial.utils.CommonUtils
import com.boxin.financial.utils.CommonUtils.fetchCode
import com.boxin.financial.utils.SPUtil
import com.boxin.financial.view.VerificationCountDownTimer
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_login_fast.*


/**
 * 快速登录
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class LoginFastFragment :
        BasePFragment<LoginFragmentContract.Presenter<String>>(R.layout.fragment_login_fast),
        LoginFragmentContract.View {

    lateinit var downTimer :VerificationCountDownTimer
    override fun attachPresenter() {
        mPresenter = LoginFragmentPresenter(this)
    }

    override fun initUI() {
        et_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_ok.isEnabled = s?.length == 11 && et_code.text.toString().length >= 4
                tv_mail.isEnabled = s?.length == 11
            }
        })
        et_code.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                btn_ok.isEnabled = s.length >= 4 && et_phone.text.length == 11
            }
        })
        if (AppConfig.PSD_FAST_CODE >= 2){
            ll_verify.visibility = View.VISIBLE
            tv_verify.text = fetchCode()
        } else {
            ll_verify.visibility = View.GONE
        }
        onClickBind(this,tv_register,tv_forget,tv_mail,btn_ok,tv_verify)
    }

    override fun initData() {
        if (!VerificationCountDownTimer.FLAG_FIRST_IN &&
                VerificationCountDownTimer.curMillis+60000L > System.currentTimeMillis()){
            setCodeTimer(VerificationCountDownTimer.curMillis+60000L - System.currentTimeMillis())
            downTimer.timerStart(false,0)
        } else {
            setCodeTimer(60000L)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_register -> {
                RegisterActivity.startAct(context!!,null)
            }
            R.id.tv_forget -> {
                ChangePasswordActivity.startAct(context!!,1)
            }
            R.id.tv_mail -> {
                val tel1 = et_phone.text.toString()
                if (!CommonUtils.matchPhone(tel1)) {
                    showToast("手机号格式不正确")
                    return
                }
                //验证码对比
                if (AppConfig.PSD_FAST_CODE >= 2 && "" == et_verify.text.toString()) {
                    showToast("图形验证码不能为空")
                    return
                }
                if (AppConfig.PSD_FAST_CODE >= 2 && !tv_verify.text.equals(et_verify.text.toString())){
                    showToast("图形验证码错误")
                    return
                } else {
                    //验证码变更
                    tv_verify.text = fetchCode()
                }
                mPresenter.fetchCode(et_phone.text.toString())
                downTimer.timerStart(true,0)
            }
            R.id.btn_ok -> {
                val tel1 = et_phone.text.toString()
                if (!CommonUtils.matchPhone(tel1)) {
                    showToast("手机号格式不正确")
                    return
                }
                //验证码对比
                if (AppConfig.PSD_FAST_CODE >= 2 && "" == et_verify.text.toString()) {
                    showToast("图形验证码不能为空")
                    return
                }
                if (AppConfig.PSD_FAST_CODE >= 2 && !tv_verify.text.equals(et_verify.text.toString())){
                    showToast("图形验证码错误")
                    return
                }
                //验证码变更
                tv_verify.text = fetchCode()
                mPresenter.onLogin(tel1,"",et_code.text.toString())
            }
            R.id.tv_verify -> {
                //验证码变更
                tv_verify.text = fetchCode()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        downTimer.cancel()
        mPresenter.close()
    }

    override fun toRegister() {
        //带着手机号去注册
        RegisterActivity.startAct(context!!,et_phone.text.toString())
    }

    override fun updateUI() {
//        MainActivity.startAct(context!!,2)
        //判断登录以后是否关闭过2次手势密码，如果没有到2次，则判断手势密码是否开启，因为默认是fase，所以只判断开启
        if(SPUtil.getShoushiLock(AppConfig.I_USER?.phone!!).isEmpty()){
            if (SPUtil.getLockNum(AppConfig.I_USER?.phone) < 2 && !AppConfig.I_USER!!.signLock) {
                GestureCreateActivity.startActivity(context!!)
            }
        }
        WebH5Activity.isLoad = true
        activity?.finish()
    }

    /**
     * 输入手机号并发送验证码
     */
    fun fastLogin(phone: String){
        if (tv_mail.isClickable) {
            et_phone.setText(phone)
            onClick(tv_mail)
        }
    }


    private fun setCodeTimer(countDownTime: Long){
        downTimer = object : VerificationCountDownTimer(countDownTime, 1000) {
            override fun onTick(l: Long) {
                if (tv_mail != null){
                    if (tv_mail.isClickable) {
                        tv_mail.isClickable = false
                    }
                    tv_mail.text = "${l / 1000}s"
                }
            }

            override fun onFinish() {
                if (tv_mail != null) {
                    tv_mail.isClickable = true
                    tv_mail.text = "重新发送"
                }
                if (countDownTime != 60000L){
                    setCodeTimer(60000L)
                }
            }
        }
    }

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(context, str)
    }

    override fun onError(error: Throwable) {
        if (AppConfig.PSD_FAST_CODE >= 2){
            ll_verify.visibility = View.VISIBLE
        } else {
            ll_verify.visibility = View.GONE
        }
    }

    override fun onDialog(show: Boolean) {
        if (show) {
            ProgressDialogUtil.getInstance().build(context!!).show()
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