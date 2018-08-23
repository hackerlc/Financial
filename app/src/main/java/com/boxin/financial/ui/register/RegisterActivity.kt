package com.boxin.financial.ui.register

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.login.LoginActivity
import com.boxin.financial.ui.login.lock.GestureCreateActivity
import com.boxin.financial.ui.web.WebActivity
import com.boxin.financial.ui.web.WebH5Activity
import com.boxin.financial.utils.CommonUtils
import com.boxin.financial.utils.SPUtil
import com.boxin.financial.view.VerificationCountDownTimer
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 注册
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RegisterActivity :
        BasePActivity<RegisterContract.Presenter<String>>(R.layout.activity_register),
        RegisterContract.View {

    var mPhone: String? = ""
    //密码可见状态
    var isOpenEye = false
    private lateinit var downTimer : VerificationCountDownTimer
    companion object {
        fun startAct(context: Context,phone: String?){
                    context.startActivity(Intent(context,RegisterActivity::class.java)
                            .putExtra("phone",phone))
        }
    }

    override fun attachPresenter() {
        mPresenter = RegisterPresenter(this,this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"注册",iv_left_img)

        mPhone = intent.getStringExtra("phone")
        btn_ok.isEnabled = false
        tv_code.isEnabled = false
        et_tel.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_ok.isEnabled = s?.length == 11 && et_psd.text.toString().length >= 6
                tv_code.isEnabled = s?.length == 11
            }
        })
        CommonUtils.setEditTextInhibitInputSpace(et_psd,20)
        et_psd.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                btn_ok.isEnabled = s.length >= 6 && et_tel.text.length == 11
            }
        })
        if (AppConfig.PSD_REGISTER_CODE >= 2){
            ll_verify.visibility = View.VISIBLE
            tv_verify.text = CommonUtils.fetchCode()
        } else {
            ll_verify.visibility = View.GONE
        }
        onClickBind(this, iv_left_img, tv_hetong, btn_ok, tv_code, iv_see,tv_verify)
    }

    override fun initData() {
        if (!VerificationCountDownTimer.FLAG_FIRST_IN_Register &&
                VerificationCountDownTimer.curMillisRegister+60000L > System.currentTimeMillis()){
            setCodeTimer(VerificationCountDownTimer.curMillisRegister+60000L - System.currentTimeMillis())
            downTimer.timerStart(false,1)
        } else {
            setCodeTimer(60000L)
        }
        if (!mPhone.isNullOrEmpty()) {
            et_tel.setText(mPhone)
            if (!VerificationCountDownTimer.FLAG_FIRST_IN_Register &&
                    VerificationCountDownTimer.curMillisRegister+60000L > System.currentTimeMillis()) {
                return
            }
            onClick(tv_code)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        downTimer.cancel()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {toExit(true)}
            R.id.tv_hetong -> {WebActivity.startAct(this,APIConfig.fetchHtmlUrl(APIConfig.HTML_URL_PRODUCT_REGISTER_AGREE,1),null)}
            R.id.btn_ok -> {
                val tel1 = et_tel.text.toString()
                if (!CommonUtils.matchPhone(tel1)) {
                    showToast("手机号格式不正确")
                    return
                }
                //验证码对比
                if (AppConfig.PSD_REGISTER_CODE >= 2 && "" == et_verify.text.toString()) {
                    showToast("图形验证码不能为空")
                    return
                }
                if (AppConfig.PSD_REGISTER_CODE >= 2 && !tv_verify.text.equals(et_verify.text.toString())){
                    showToast("图形验证码错误")
                    return
                }
                //密码验证
                val psd = et_psd.text.toString()
                if (!CommonUtils.matchPassword(psd)){
                    showToast("密码中使用了非法字符，请使用a-z、A-Z、0-9和常用英文标点符号")
                    return
                }
                //验证码变更
                tv_verify.text = CommonUtils.fetchCode()
                mPresenter.onRegister(tel1,psd,et_code.text.toString())
            }
            R.id.tv_code -> {
                val tel1 = et_tel.text.toString()
                if (!CommonUtils.matchPhone(tel1)) {
                    showToast("手机号格式不正确")
                    return
                }
                //验证码对比
                if (AppConfig.PSD_REGISTER_CODE >= 2 && "" == et_verify.text.toString()) {
                    showToast("图形验证码不能为空")
                    return
                }
                if (AppConfig.PSD_REGISTER_CODE >= 2 && !tv_verify.text.equals(et_verify.text.toString())){
                    showToast("图形验证码错误")
                    return
                } else {
                    //验证码变更
                    tv_verify.text = CommonUtils.fetchCode()
                }
                mPresenter.fetchCode(tel1,APIConfig.VERIFY_CODE_REGISTER)
                //倒计时
                timerStart()
            }
            R.id.iv_see -> {
                //显示隐藏密码
                if (isOpenEye){
                    isOpenEye = false
                    et_psd.transformationMethod = PasswordTransformationMethod.getInstance()
                    iv_see.setImageDrawable(resources.getDrawable(R.drawable.bg_see_close))
                } else {
                    isOpenEye = true
                    et_psd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    iv_see.setImageDrawable(resources.getDrawable(R.drawable.bg_see))
                }
            }
            R.id.tv_verify -> {
                //验证码变更
                tv_verify.text = CommonUtils.fetchCode()
            }
        }
    }

    override fun timerStart() {
        downTimer.timerStart(true,1)
    }

    /**
     * 用户已经注册，跳转到验证码登录界面
     */
    override fun toLogin() {
        LoginActivity.startAct(this,1,et_tel.text.toString())
        toExit(true)
    }


    override fun updateUI() {
        //登录成功UI显示
//        MainActivity.startAct(this,2)
//        toExit(true)
        if(SPUtil.getShoushiLock(AppConfig.I_USER?.phone!!).isEmpty()){
            if (SPUtil.getLockNum(AppConfig.I_USER?.phone) < 2 && !AppConfig.I_USER!!.signLock) {
                GestureCreateActivity.startActivity(this)
            }
        }
        WebH5Activity.isLoad = true
        finish()
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

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(this, str)
    }

    override fun onError(error: Throwable) {
        if (AppConfig.PSD_REGISTER_CODE >= 2){
            ll_verify.visibility = View.VISIBLE
        } else {
            ll_verify.visibility = View.GONE
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