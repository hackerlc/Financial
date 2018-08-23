package com.boxin.financial.ui.my.capital.flow.repayment.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.entity.ProductInfo
import com.boxin.financial.entity.RepaymentDetail
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.utils.CommonUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_repayment_detail.*
import kotlinx.android.synthetic.main.head_common.*
import kotlinx.android.synthetic.main.item_investment_pan.view.*

/**
 * 回款详情
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RepaymentDetailActivity :
        BasePActivity<RepaymentDetailContract.Presenter<RepaymentDetail>>(R.layout.activity_repayment_detail),
        RepaymentDetailContract.View {
    companion object {
        /**
         * @param type 回款类型 0待回款 1已回款
         */
        fun startAct(context: Context,type: Int,id: String?){
                    context.startActivity(Intent(context,RepaymentDetailActivity::class.java)
                            .putExtra("type",type)
                            .putExtra("id",id))
        }
    }

    var type = 0
    var id :String? = null

    override fun attachPresenter() {
        id = intent.getStringExtra("id")
        if (id.isNullOrEmpty()){
            ToastUtil.getInstance().makeShortToast(this,"无法查看回款详情")
            toExit(true)
        }
        mPresenter = RepaymentDetailPresenter(this,id!!)
    }

    override fun initUI() {
        type = intent.getIntExtra("type",0)
        onHeadChange(head_top,tv_title,if (type==0) "待回款详情" else "已回款详情",iv_left_img)

        onClickBind(this,iv_left_img)
    }

    override fun initData() {

        mPresenter.fetch()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {toExit(true)}
        }
    }

    override fun updateUI() {
        tv_name.text = mPresenter.getData().name
        tv_type.text = if (mPresenter.getData().type) "已回款" else "待回款"
        tv_ed_all_money.text = "${CommonUtils.onFormatTwo(mPresenter.getData().already!!.total)}元"
        tv_ed_money.text = "${CommonUtils.onFormatTwo(mPresenter.getData().already!!.amount)}元"
        tv_ed_profit.text = "${CommonUtils.onFormatTwo(mPresenter.getData().already!!.income)}元"
        if (mPresenter.getData().type) {
            rl_ing.visibility = View.GONE
        } else {
            tv_ing_all_money.text = "${CommonUtils.onFormatTwo(mPresenter.getData().pending!!.total)}元"
            tv_ing_money.text = "${CommonUtils.onFormatTwo(mPresenter.getData().pending!!.amount)}元"
            tv_ing_profit.text = "${CommonUtils.onFormatTwo(mPresenter.getData().pending!!.income)}元"
        }
        tv_product_no.text = mPresenter.getData().assetInfo!!.id
        tv_interest_rate.text = "${CommonUtils.onFormatTwo(mPresenter.getData().assetInfo!!.profit)}%"
        tv_date.text = "${mPresenter.getData().assetInfo!!.time+mPresenter.getData().assetInfo!!.interestTypeBoStr}"
        tv_style.text = mPresenter.getData().assetInfo!!.type?.text
        tv_pay_money.text ="${CommonUtils.onFormatTwo(mPresenter.getData().assetInfo!!.allMoney)}元"
        tv_product.text = "${CommonUtils.onFormatTwo(mPresenter.getData().assetInfo!!.income)}元"

        //ll_investment_div add view
        val inflater =  getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        for (list in mPresenter.getData().repayments) {
            val investmentView = inflater.inflate(R.layout.item_investment_pan, ll_investment_div, false)
            investmentView.tv_date.text = list.date
            investmentView.tv_all_money.text = list.allMoney
            investmentView.tv_money.text = list.money
            investmentView.tv_interest.text =list.interest
            ll_investment_div.addView(investmentView)
        }
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