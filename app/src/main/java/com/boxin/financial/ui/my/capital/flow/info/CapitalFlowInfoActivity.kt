package com.boxin.financial.ui.my.capital.flow.info

import android.content.Context
import android.content.Intent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.entity.CapitalFlowGroup
import com.boxin.financial.extended.initRecyclerView
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onDefaultSmartRefreshLayout
import com.boxin.financial.extended.onHeadChange
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_capital_flow_info.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 资金流水
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class CapitalFlowInfoActivity :
        BasePActivity<CapitalFlowInfoContract.Presenter<MutableList<CapitalFlowGroup>>>(R.layout.activity_capital_flow_info),
        CapitalFlowInfoContract.View {
    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context,CapitalFlowInfoActivity::class.java))
        }
    }

    lateinit var mAdapter: CapitalFlowInfoGroupAdapter

    override fun attachPresenter() {
        mPresenter = CapitalFlowInfoPresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"资金明细",iv_left_img)

        rv_view.initRecyclerView()

        sr_layout.onDefaultSmartRefreshLayout()
        sr_layout.setOnRefreshListener{
            mPresenter.refreshData()
        }
        sr_layout.setOnLoadmoreListener{
            mPresenter.fetchMore()
        }

        onClickBind(this, iv_left_img)
    }

    override fun initData() {
        mAdapter = CapitalFlowInfoGroupAdapter(null)
        rv_view.adapter = mAdapter

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
        if (sr_layout.isRefreshing) {
            sr_layout.finishRefresh(500)
        }
        if (sr_layout.isLoading){
            sr_layout.finishLoadmore(500)
        }
        mAdapter.update(mPresenter.getData())

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