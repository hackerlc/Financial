package com.boxin.financial.ui.financial.info

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.View
import android.widget.ProgressBar
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.entity.Financial
import com.boxin.financial.utils.CommonUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_financial_info.*


@SuppressLint("ValidFragment")
/**
 * 标的详情信息
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialInfoFragment(var mFinancial: Financial) :
        BasePFragment<FinancialInfoContract.Presenter<Financial>>(R.layout.fragment_financial_info),
        FinancialInfoContract.View {

    override fun attachPresenter() {
        mPresenter = FinancialInfoPresenter(this)
    }

    override fun initUI() {
    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    fun notifyDataSetChanged(financial: Financial){
        mFinancial = financial
        updateUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun updateUI() {
        tv_interest_rate.text = "${CommonUtils.onFormat(mFinancial.yearProfit)}%"
        tv_all_money.text = CommonUtils.addComma(CommonUtils.onFormatTwo(mFinancial.allMoney))
        //日息或者月息
        val strShowTime = mFinancial.interestTypeBoStr
        tv_date.text = "${mFinancial.time}$strShowTime"
        tv_money.text = "${mFinancial.lave}"
        tv_start_money.text = "起投金额${mFinancial.startMoney}元"
        tv_progress.text = "${mFinancial.getPro()}%"
        pb_data.progress = mFinancial.getPro()
        //进度条 70%
//        setAnimation(pb_data, mFinancial.getPro())
        tv_name.text = mFinancial.name
        tv_type.text = mFinancial.typeStr
        tv_style.text = mFinancial.typeStr
        tv_start_date.text = mFinancial.startTimeStr
        rb_level.rating = mFinancial.save*1.0f
    }

    private fun setAnimation(view: ProgressBar, mProgressBar: Int) {
        val animator = ValueAnimator.ofInt(0, mProgressBar).setDuration(3000)

        animator.addUpdateListener { valueAnimator -> view.progress = valueAnimator.animatedValue as Int }
        animator.start()
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