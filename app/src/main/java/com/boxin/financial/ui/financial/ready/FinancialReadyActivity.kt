package com.boxin.financial.ui.financial.ready

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.Financial
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.extended.onInitHead
import com.boxin.financial.ui.my.recharge.RechargeActivity
import com.boxin.financial.ui.pay.FinancialPayActivity
import com.boxin.financial.utils.CommonUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_financial_ready.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 授权出借
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialReadyActivity :
        BasePActivity<FinancialReadyContract.Presenter<String>>(R.layout.activity_financial_ready),
        FinancialReadyContract.View {

    lateinit var mFinancial: Financial
    companion object {
        fun startAct(context: Context,financial: Financial){
                    context.startActivity(Intent(context,FinancialReadyActivity::class.java)
                            .putExtra("financial",financial))
        }
    }

    override fun attachPresenter() {
        mPresenter = FinancialReadyPresenter(this)
    }

    override fun initUI() {
        mFinancial = intent.getParcelableExtra("financial")
        if (mFinancial == null){
            showToast("无法出借")
            toExit(true)
        }
        onHeadChange(head_top,tv_title,"授权出借",iv_left_img)

        et_money.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //计算收益
                if (!s.isNullOrEmpty()) {
                    tv_profit.text = "${CommonUtils.onFormatTwo(s.toString().toDouble()*mFinancial.profit!!.toDouble()*(mFinancial.time!!.toDouble()))}元"
                } else {
                    tv_profit.text = ""
                }
            }

        })
        //显示
        tv_interest_rate.text = "${CommonUtils.onFormat(mFinancial.yearProfit)}%"
        tv_all_money.text = CommonUtils.addComma(CommonUtils.onFormatTwo(mFinancial.lave))
        //日息或者月息
        val strShowTime = mFinancial.interestTypeBoStr
        tv_date.text = "${mFinancial.time}$strShowTime"
        //起投金额
        et_money.hint = "请输入投资金额,${mFinancial.startMoney}元起投"
        tv_balance.text = "${CommonUtils.addComma(AppConfig.I_ACCOUNT?.moneyShow())}元"
        onClickBind(this, btn_ok,iv_left_img,tv_pay_all_money)
    }

    override fun initData() {
    }

    override fun onStart() {
        super.onStart()
        mPresenter.fetchUserInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_ok -> {
                //首先判断投资金额是否为
                val money = et_money.text.toString().trim()
                if (money.isNullOrEmpty()){
                    showToast("投资额不能为空")
                    return
                }
                if (!CommonUtils.isNumeric(money)){
                    showToast("请输入正确金额")
                    return
                }
                //起投金额判断
                if (money.toDouble() < mFinancial.startMoney){
                    showToast("投资金额需大于等于${mFinancial.startMoney}")
                    return
                }
                //剩余可投金额判断
                if (money.toDouble() > mFinancial.lave) {
                    showToast("投资金额不能大于剩余可投金额")
                    return
                }
                //余额判断，高于余额充值，低与余额跳转
                if (money.toDouble() > AppConfig.I_ACCOUNT!!.money){
                    RechargeActivity.startAct(this,"${money.toDouble() - AppConfig.I_ACCOUNT!!.money}")
                } else {
                    FinancialPayActivity.startAct(this,mFinancial,money)
                }
            }
            R.id.iv_left_img -> {
                toExit(true)
            }
            R.id.tv_pay_all_money -> {
                //点击余额全投,判断余额是否大于剩余金额
                if(AppConfig.I_ACCOUNT!!.money >= mFinancial.lave){
                    et_money.setText("${mFinancial.lave}")
                } else {
                    et_money.setText("${AppConfig.I_ACCOUNT?.money}")
                }
            }
        }
    }

    override fun updateUI() {
        tv_balance.text = "${CommonUtils.addComma(AppConfig.I_ACCOUNT?.moneyShow())}元"
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