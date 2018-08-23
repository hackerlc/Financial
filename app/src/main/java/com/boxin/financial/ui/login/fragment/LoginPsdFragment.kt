package com.boxin.financial.ui.login.fragment

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
import com.boxin.financial.utils.SPUtil
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_login_psd.*

/**
 * 密码登录
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class LoginPsdFragment :
        BasePFragment<LoginFragmentContract.Presenter<String>>(R.layout.fragment_login_psd),
        LoginFragmentContract.View {
    var isOpenEye = false

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
                btn_ok.isEnabled = s?.length == 11 && et_psd.text.toString().length >= 6
            }
        })
        CommonUtils.setEditTextInhibitInputSpace(et_psd,20)
        et_psd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                btn_ok.isEnabled = s.length >= 6 && et_phone.text.length == 11
            }
        })
        onClickBind(this,tv_register,tv_forget,iv_see,btn_ok)
    }

    override fun initData() {
        //获取密码记录，错误三次提示并跳转fast页面

    }

    override fun toRegister() {
        RegisterActivity.startAct(context!!,et_phone.text.toString())
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_register -> {
                RegisterActivity.startAct(context!!,null)
            }
            R.id.tv_forget -> {
                ChangePasswordActivity.startAct(context!!,1)
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
            R.id.btn_ok -> {
                //登录
                val tel1 = et_phone.text.toString()
                if (!CommonUtils.matchPhone(tel1)) {
                    showToast("手机号格式不正确")
                    return
                }
                //密码验证
                val psd = et_psd.text.toString()
                if (!CommonUtils.matchPassword(psd)){
                    showToast("密码中使用了非法字符，请使用a-z、A-Z、0-9和常用英文标点符号")
                    return
                }
                //判断map中是否有phone，若没有则添加，有则不添加只判断次数
                if (CommonUtils.onPhonePasswordCheck(tel1)){
                    showToast("您的登录密码已经输错3次，请使用快速登录")
                    return
                }
                mPresenter.onLogin(tel1,et_psd.text.toString(),"")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun updateUI() {
//        MainActivity.startAct(context!!,2)
        //登录成功UI显示
        if(SPUtil.getShoushiLock(AppConfig.I_USER?.phone!!).isEmpty()){
            if (SPUtil.getLockNum(AppConfig.I_USER?.phone) < 2 && !AppConfig.I_USER!!.signLock) {
                GestureCreateActivity.startActivity(context!!)
            }
        }
        WebH5Activity.isLoad = true
        activity?.finish()
    }

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(context, str)
    }

    override fun onError(error: Throwable) {

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