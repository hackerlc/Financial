package com.boxin.financial.ui.investment.pan

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.entity.InvestmentPanInfo
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.investment.InvestmentActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_investment_pan.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 回款计划
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class InvestmentPanActivity :
        BasePActivity<InvestmentPanContract.Presenter<MutableList<InvestmentPanInfo>>>(R.layout.activity_investment_pan),
        InvestmentPanContract.View {

    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context,InvestmentPanActivity::class.java))
        }
    }

    lateinit var mAdapter: InvestmentPanAdapter

    override fun attachPresenter() {
        mPresenter = InvestmentPanPresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"回款计划",iv_left_img)
        rv_view.layoutManager = LinearLayoutManager(this)

        onClickBind(this,iv_left_img)
    }

    override fun initData() {
        mAdapter = InvestmentPanAdapter(mPresenter.getData())
        rv_view.adapter = mAdapter

        mPresenter.fetch()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    override fun updateUI() {
        mAdapter.notifyDataSetChanged()
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