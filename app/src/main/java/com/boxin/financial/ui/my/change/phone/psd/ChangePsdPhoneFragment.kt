package com.boxin.financial.ui.my.change.phone.psd

import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.config.AppConfig
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.my.change.phone.enter.EnterNewPhoneActivity
import com.boxin.financial.utils.CommonUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_change_psd_phone.*

/**
 * 使用密码方式修改手机号
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ChangePsdPhoneFragment :
        BasePFragment<ChangePsdPhoneContract.Presenter<String>>(R.layout.fragment_change_psd_phone),
        ChangePsdPhoneContract.View {
    //密码可见状态
    var isOpenEye = false

    override fun attachPresenter() {
        mPresenter = ChangePsdPhonePresenter(this)
    }

    override fun initUI() {
        et_phone.setText(AppConfig.I_USER?.phone)
        et_phone.isEnabled = false

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
        onClickBind(this,iv_see,btn_ok,iv_see)
    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        when (v.id) {
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
                mPresenter.onCheckPhone(tel1,et_psd.text.toString(),"")
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onCheckSuccess() {
        EnterNewPhoneActivity.startAct(context!!)
        activity?.finish()
    }

    override fun updateUI() {
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