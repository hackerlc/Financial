package com.boxin.financial.ui.my.recharge

import android.content.Context
import android.content.Intent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.R.id.*
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.base.glide.AGlide
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.AccountOrder
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.web.WebActivity
import com.boxin.financial.utils.CommonUtils
import com.boxin.financial.utils.CommonUtils.fetchBankCard4Char
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_recharge.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 充值页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RechargeActivity :
        BasePActivity<RechargeContract.Presenter<AccountOrder>>(R.layout.activity_recharge),
        RechargeContract.View {

    companion object {
        const val F_MONEY = "0.0"
        /**
         * @param money
         * @param context
         */
        fun startAct(context: Context,money: String = F_MONEY){
                    context.startActivity(Intent(context,RechargeActivity::class.java)
                            .putExtra("money",money))
        }
    }

    var mMoney = F_MONEY

    override fun attachPresenter() {
        mPresenter = RechargePresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"充值",iv_left_img)
        mMoney = intent.getStringExtra("money")
        if (mMoney.isNullOrEmpty()){
            mMoney = F_MONEY
        }

        et_money.setText(mMoney)
        tv_balance.text = "账户余额：${CommonUtils.addComma(AppConfig.I_ACCOUNT?.moneyShow())}元"

        //判断银行卡是否绑定，若没有绑定隐藏银行卡信息
        onClickBind(this,iv_left_img,btn_ok)
    }

    override fun initData() {

    }

    override fun onStart() {
        super.onStart()
        if (AppConfig.I_ACCOUNT?.bankInfo() != null){
            showBankInfo()
        } else {
            mPresenter.fetchBankCard()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun showBindingBank(html: String) {
        WebActivity.startAct(this,html,"",1)
    }

    override fun showHtml(html: String) {
        WebActivity.startAct(this,html,"",1)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {
                toExit(true)
            }
            R.id.btn_ok -> {
                //银行存管充值页面
                val money = et_money.text.toString().trim()
                if (!CommonUtils.isNumeric(money)){
                    showToast("请输入正确金额")
                    return
                }

                if (money.toDouble() < 100){
                    showToast("充值金额最低100元起")
                    return
                }
                //判断是否绑卡，若没有绑卡则请求绑卡接口
                if (AppConfig.I_ACCOUNT?.bankInfo() != null){
                    mPresenter.fetch(money)
                } else {
                    mPresenter.fetch()
                }

            }
        }
    }

    override fun updateUI() {
        showBankInfo()
    }

    private fun showBankInfo(){
        rl_bank.visibility = View.VISIBLE
        AGlide.with(this).load(AppConfig.I_ACCOUNT?.bankInfo()?.logo).fitCenter().into(iv_blank_img)
        tv_blank_name.text = AppConfig.I_ACCOUNT?.bankInfo()?.name
        tv_blank_id.text = CommonUtils.fetchBankCard4Char(AppConfig.I_ACCOUNT?.bankInfo()?.cardId)
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