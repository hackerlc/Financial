package com.boxin.financial.ui.my.balance

import android.content.Context
import android.content.Intent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.my.capital.flow.recharge.withdraw.CapitalFlowRWActivity
import com.boxin.financial.ui.my.recharge.RechargeActivity
import com.boxin.financial.ui.my.withdraw.WithdrawActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_balance.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 我的余额
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class BalanceActivity :
        BasePActivity<BalanceContract.Presenter<String>>(R.layout.activity_balance),
        BalanceContract.View {

    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context,BalanceActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = BalancePresenter(this)
    }

    override fun initUI() {
        tv_title.text = "我的余额"
        iv_left_img.visibility = View.VISIBLE

        onClickBind(this,iv_left_img, btn_recharge,btn_withdraw,tv_record)
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {toExit(true)}
            R.id.btn_recharge -> {
                RechargeActivity.startAct(this)
            }
            R.id.btn_withdraw -> {
                WithdrawActivity.startAct(this)
            }
            R.id.tv_record -> {
                CapitalFlowRWActivity.startAct(this)
            }
        }
    }

    override fun updateUI() {
        TODO("not implemented") //To change FieldMap of created functions use File | Settings | File Templates.
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