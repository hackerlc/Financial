package com.boxin.financial.ui.password.fragment

import android.annotation.SuppressLint
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.config.AppConfig.Companion.I_USER
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.login.LoginActivity
import com.boxin.financial.ui.web.WebH5Activity
import com.boxin.financial.utils.CommonUtils
import com.boxin.financial.view.VerificationCountDownTimer
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.rxjava.rxbus.RxBus
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_change_password.*

@SuppressLint("ValidFragment")
/**
 * 修改密码公用页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 *
 * @param type 0 修改密码 1 忘记密码
 */
class ChangePasswordFragment(val type: Int) :
        BasePFragment<ChangePasswordContract.Presenter<String>>(R.layout.fragment_change_password),
        ChangePasswordContract.View {
    //密码可见状态
    var isOpenEye = false
    private lateinit var downTimer : VerificationCountDownTimer

    override fun attachPresenter() {
        mPresenter = ChangePasswordPresenter(this)
    }

    override fun initUI() {
        btn_ok.isEnabled = false
        btn_ok.text = "确认修改"
        tv_code.isEnabled = false
        et_tel.addTextChangedListener(object : TextWatcher {
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
        et_psd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                btn_ok.isEnabled = s.length >= 6 && et_tel.text.length == 11
            }
        })
        tv_hetong.visibility = View.GONE
        hetong.visibility = View.GONE
        if (type == 0 && AppConfig.IS_LOGIN){
            et_tel.setText(CommonUtils.changeStrTrip(I_USER?.phone))
            et_tel.isEnabled = false
        }
        if (AppConfig.PSD_PASSWORD_CODE >= 2){
            ll_verify.visibility = View.VISIBLE
            tv_verify.text = CommonUtils.fetchCode()
        } else {
            ll_verify.visibility = View.GONE
        }
        onClickBind(this, btn_ok, tv_code, iv_see, tv_verify)
    }

    override fun initData() {
        if (!VerificationCountDownTimer.FLAG_FIRST_IN_Password &&
                VerificationCountDownTimer.curMillisPassword+60000L > System.currentTimeMillis()){
            setCodeTimer(VerificationCountDownTimer.curMillisPassword+60000L - System.currentTimeMillis())
            downTimer.timerStart(false,2)
        } else {
            setCodeTimer(60000L)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_ok -> {
                val tel1: String? = if (type == 0){
                    I_USER?.phone
                } else {
                    et_tel.text.toString()
                }
                if (!CommonUtils.matchPhone(tel1)) {
                    showToast("手机号格式不正确")
                    return
                }
                val code = et_code.text.toString()
                if (code.isNullOrEmpty()){
                    showToast("验证码不能为空")
                    return
                }
                //验证码对比
                if (AppConfig.PSD_PASSWORD_CODE >= 2 && "" == et_verify.text.toString()) {
                    showToast("图形验证码不能为空")
                    return
                }
                if (AppConfig.PSD_PASSWORD_CODE >= 2 && !tv_verify.text.equals(et_verify.text.toString())){
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
                if (type == 0){
                    mPresenter.onChangePassword(tel1!!,et_psd.text.toString(),et_code.text.toString(),APIConfig.VERIFY_CODE_CHANGE_PASSWORD)
                } else {
                    mPresenter.onChangePassword(tel1!!,et_psd.text.toString(),et_code.text.toString(),APIConfig.VERIFY_CODE_CHANGE_PASSWORD_FORGET)
                }
            }
            R.id.tv_code -> {
                val tel1: String? = if (type == 0){
                    I_USER?.phone
                } else {
                    et_tel.text.toString()
                }
                if (!CommonUtils.matchPhone(tel1)) {
                    showToast("手机号格式不正确")
                    return
                }
                //验证码对比
                if (AppConfig.PSD_PASSWORD_CODE >= 2 && "" == et_verify.text.toString()) {
                    showToast("图形验证码不能为空")
                    return
                }
                if (AppConfig.PSD_PASSWORD_CODE >= 2 && !tv_verify.text.equals(et_verify.text.toString())){
                    showToast("图形验证码错误")
                    return
                } else {
                    //验证码变更
                    tv_verify.text = CommonUtils.fetchCode()
                }
                if (type == 0) {
                    mPresenter.fetchCode(tel1!!, APIConfig.VERIFY_CODE_CHANGE_PASSWORD)
                } else {
                    mPresenter.fetchCode(tel1!!, APIConfig.VERIFY_CODE_CHANGE_PASSWORD_FORGET)
                }
                //倒计时
                downTimer.timerStart(true,2)
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

    override fun onDestroy() {
        super.onDestroy()
        downTimer.cancel()
        mPresenter.close()
    }

    override fun updateUI() {
        //修改成功判断跳转
        if (type == 0 ){
            WebH5Activity.isLoad = true
            RxBus.getInstance().post(RxBus.TAG_OTHER,true)
            LoginActivity.startAct(context!!,0)
        }
        activity?.finish()

    }

    private fun setCodeTimer(countDownTime: Long) {
        downTimer = object : VerificationCountDownTimer(countDownTime, 1000) {
            override fun onTick(l: Long) {
                if (tv_code != null) {
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
                if (countDownTime != 60000L) {
                    setCodeTimer(60000L)
                }
            }
        }
    }

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(context, str)
    }

    override fun onError(error: Throwable) {
        if (AppConfig.PSD_PASSWORD_CODE >= 2){
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