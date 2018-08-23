package com.boxin.financial.ui.investment.records

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.entity.InvestmentRecordInfo
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onDefaultSmartRefreshLayout
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.investment.InvestmentActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.ui.custom.GearRecyclerItemDecoration
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_investment_records.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 投资记录
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class InvestmentRecordsActivity :
        BasePActivity<InvestmentRecordsContract.Presenter<MutableList<InvestmentRecordInfo>>>(R.layout.activity_investment_records),
        InvestmentRecordsContract.View {
    lateinit var mAdapter: InvestmentRecordsAdapter
    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context, InvestmentRecordsActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = InvestmentRecordsPresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"投资记录",iv_left_img)
        rv_view.layoutManager = LinearLayoutManager(this)
        rv_view.addItemDecoration(GearRecyclerItemDecoration(this, LinearLayoutManager.VERTICAL,10))

        sr_layout.onDefaultSmartRefreshLayout()
        sr_layout.setOnRefreshListener{
            mPresenter.refreshData()
        }
        sr_layout.setOnLoadmoreListener{
            mPresenter.fetchMore()
        }
        onClickBind(this,iv_left_img)
    }

    override fun initData() {
        mAdapter = InvestmentRecordsAdapter(mPresenter.getData())
        rv_view.adapter = mAdapter
        mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener {
            adapter, view, position ->
            InvestmentActivity.startAct(this,mPresenter.getData()[position].id!!)
        }
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
        mAdapter.notifyDataSetChanged()
    }

    override fun closeMore() {
        sr_layout.setLoadmoreFinished(true)
    }

    override fun showMore() {
        sr_layout.setLoadmoreFinished(false)
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