package com.boxin.financial.ui.my.withdraw

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.AccountOrder
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.web.WebActivity
import com.boxin.financial.utils.CommonUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_withdraw.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 提现
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class WithdrawActivity :
        BasePActivity<WithdrawContract.Presenter<AccountOrder>>(R.layout.activity_withdraw),
        WithdrawContract.View {

    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context,WithdrawActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = WithdrawPresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"提现",iv_left_img)
        tv_balance.text = "${AppConfig.I_ACCOUNT?.moneyShow()}元"
        cb_pt.isChecked = true
        showPayMessage()

        et_money.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    if (cb_pt.isChecked) {
                        tv_fast_money.text = "实际到账：${showPrice(1,s.toString())}元"
                    } else {
                        tv_fast_money.text = "实际到账：${showPrice(2,s.toString())}元"
                        tv_fast_price.text = "提现手续费:${showPrice(0,s.toString())}元"
                    }
                } else {
                    tv_fast_money.text = "实际到账：0.00元"
                }
            }

        })
        onClickBind(this, iv_left_img,tv_all_money,ll_fast,ll_pt,cb_pt,cb_fast,btn_ok)
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun showHtml(html: String) {
        WebActivity.startAct(this,html,"",1)
    }

    private fun showPayMessage(){
        var message = ""
        rl_fast.visibility = View.VISIBLE
        if (cb_pt.isChecked){
            //普通提现
            if (AppConfig.I_ACCOUNT?.freeOut!! > 0 ){
                message = "本月还有${AppConfig.I_ACCOUNT!!.freeOut}次普通提现，免费提现机会。"
                rl_fast.visibility = View.GONE
            } else {
                message = "本月免费提现次数已用完，需支付提现手续费"
            }
            //手续费2元
            tv_fast_price.text = "提现手续费:2.00元"
            tv_fast_money.text = "实际到账：${showPrice(1,et_money.text.toString())}元"
        } else {
            //快速提现
            message = "快速提现，需支付提现手续费"
            tv_fast_price.text = "提现手续费:${showPrice(0,et_money.text.toString())}元"
            tv_fast_money.text = "实际到账：${showPrice(2,et_money.text.toString())}元"
        }
        tv_message.text = message
    }

    /**
     * @param type 0普通提现 1快速提现手续费 2快速提现到账金额
     */
    private fun showPrice(type: Int,price: String?): String{
        var num = 0.00
        if (price.isNullOrEmpty()){
            return "0.00"
        }
        when(type){
            0 -> {
                num = price!!.toDouble() * 0.001 + 2.00
            }
            1 -> {
                num = price!!.toDouble() - 2.00
            }
            2 -> {
                num = price!!.toDouble() - (price.toDouble() * 0.001 + 2.00)
            }
        }
        return CommonUtils.onFormatTwo(num)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {toExit(true)}
            R.id.tv_all_money -> {
                //余额全提
                et_money.setText(AppConfig.I_ACCOUNT?.moneyShow())
            }
            R.id.ll_pt,R.id.cb_pt -> {
                cb_pt.isChecked = true
                cb_fast.isChecked = false
                showPayMessage()
            }
            R.id.ll_fast,R.id.cb_fast -> {
                cb_pt.isChecked = false
                cb_fast.isChecked = true
                showPayMessage()
            }
            R.id.btn_ok -> {
                val money = et_money.text.toString().trim()
                if (!CommonUtils.isNumeric(money)){
                    showToast("请输入正确金额")
                    return
                }

                if (money.toDouble() < 100){
                    showToast("提现金额最低100元起")
                    return
                }
                if (money.toDouble() > 1000000.00){
                    showToast("提现金额最高1,000,000.00元")
                    return
                }

                mPresenter.fetch(money,cb_fast.isChecked)
            }
        }
    }

    override fun updateUI() {
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