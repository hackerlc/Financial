package com.boxin.financial.ui.pay

import android.content.Context
import android.content.Intent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.Financial
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.success.SuccessActivity
import com.boxin.financial.ui.web.WebActivity
import com.boxin.financial.utils.CommonUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_financial_pay.*

/**
 * 支付页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialPayActivity :
        BasePActivity<FinancialPayContract.Presenter<String>>(R.layout.activity_financial_pay),
        FinancialPayContract.View {

    lateinit var mFinancial: Financial
    lateinit var mPayMoney: String

    companion object {
        /**
         * @param financial
         * @param payMoney
         */
        fun startAct(context: Context,financial: Financial,payMoney: String){
                    context.startActivity(Intent(context, FinancialPayActivity::class.java)
                            .putExtra("financial",financial)
                            .putExtra("payMoney",payMoney))
        }
    }

    override fun attachPresenter() {
        mPresenter = FinancialPayPresenter(this)
    }

    override fun initUI() {
        mFinancial = intent.getParcelableExtra("financial")
        if (mFinancial == null){
            showToast("授权失败")
            toExit(true)
        }
        mPayMoney = intent.getStringExtra("payMoney")

        tv_pay_money.text = mPayMoney
        tv_profit.text = "${CommonUtils.addComma(AppConfig.I_ACCOUNT?.moneyShow())}"
        onClickBind(this, btn_pay,div2)
    }

    override fun initData() {
    }

    override fun showHtml(str: String) {
        WebActivity.startAct(this,str,"",1)
        toExit(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_pay -> {
                mPresenter.fetch(mPayMoney,mFinancial.id!!)
            }
            R.id.div2 -> {
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