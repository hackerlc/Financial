package com.boxin.financial.ui.my.change.phone.code

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.config.AppConfig
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.my.change.phone.enter.EnterNewPhoneActivity
import com.boxin.financial.utils.CommonUtils
import com.boxin.financial.view.VerificationCountDownTimer
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_change_phone.*

/**
 * 修改原手机号
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ChangeOldPhoneFragment :
        BasePFragment<ChangeOldPhoneContract.Presenter<String>>(R.layout.fragment_change_phone),
        ChangeOldPhoneContract.View {

    lateinit var downTimer : VerificationCountDownTimer

    override fun attachPresenter() {
        mPresenter = ChangeOldPhonePresenter(this)
    }

    override fun initUI() {
        et_tel.setText(AppConfig.I_USER?.phone)
        et_tel.isEnabled = false
        btn_ok.isEnabled = false

        et_code.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                btn_ok.isEnabled = s.length >= 4
            }
        })
        if (AppConfig.PSD_CHANGE_OLD >= 2){
            ll_verify.visibility = View.VISIBLE
            tv_verify.text = CommonUtils.fetchCode()
        } else {
            ll_verify.visibility = View.GONE
        }
        onClickBind(this,btn_ok,tv_code,tv_verify)
    }

    override fun initData() {
        if (!VerificationCountDownTimer.FLAG_FIRST_IN &&
                VerificationCountDownTimer.curMillis+60000L > System.currentTimeMillis()){
            setCodeTimer(VerificationCountDownTimer.curMillis+60000L - System.currentTimeMillis())
            downTimer.timerStart(false,3)
        } else {
            setCodeTimer(60000L)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_code -> {
                val tel1 = et_tel.text.toString()
                //验证码对比
                if (AppConfig.PSD_CHANGE_OLD >= 2 && "" == et_verify.text.toString()) {
                    showToast("图形验证码不能为空")
                    return
                }
                if (AppConfig.PSD_CHANGE_OLD >= 2 && !tv_verify.text.equals(et_verify.text.toString())){
                    showToast("图形验证码错误")
                    return
                } else {
                    //验证码变更
                    tv_verify.text = CommonUtils.fetchCode()
                }
                mPresenter.fetchCode(tel1)
                downTimer.timerStart(true,3)
            }
            R.id.btn_ok -> {
                val tel1 = et_tel.text.toString()
                if (!CommonUtils.matchPhone(tel1)) {
                    showToast("手机号格式不正确")
                    return
                }
                //验证码对比
                if (AppConfig.PSD_CHANGE_OLD >= 2 && "" == et_verify.text.toString()) {
                    showToast("图形验证码不能为空")
                    return
                }
                if (AppConfig.PSD_CHANGE_OLD >= 2 && !tv_verify.text.equals(et_verify.text.toString())){
                    showToast("图形验证码错误")
                    return
                }
                //验证码变更
                tv_verify.text = CommonUtils.fetchCode()
                mPresenter.onChange(tel1,"",et_code.text.toString())
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
    }

    override fun checkSuccess() {
        EnterNewPhoneActivity.startAct(context!!)
        activity?.finish()
    }

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(context, str)
    }

    override fun onError(error: Throwable) {
        if (AppConfig.PSD_CHANGE_OLD >= 2){
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