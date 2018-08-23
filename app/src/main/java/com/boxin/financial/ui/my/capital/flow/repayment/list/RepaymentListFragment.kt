package com.boxin.financial.ui.my.capital.flow.repayment.list

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ExpandableListView
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.entity.RepaymentGroup
import com.boxin.financial.entity.RepaymentInfo
import com.boxin.financial.extended.initRecyclerView
import com.boxin.financial.extended.initSmartRefreshLayout
import com.boxin.financial.extended.onDefaultSmartRefreshLayout
import com.boxin.financial.ui.my.capital.flow.repayment.RepaymentActivity
import com.boxin.financial.ui.my.capital.flow.repayment.detail.RepaymentDetailActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.githang.groundrecycleradapter.OnChildClickListener
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.ui.custom.GearRecyclerItemDecoration
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_repayment_list.*

@SuppressLint("ValidFragment")
/**
 * 回款流水列表
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RepaymentListFragment(var type: Int) :
        BasePFragment<RepaymentListContract.Presenter<MutableList<RepaymentGroup>>>(R.layout.fragment_repayment_list),
        RepaymentListContract.View {

    lateinit var mAdapter: RepaymentGroupAdapter

    override fun attachPresenter() {
        mPresenter = RepaymentListPresenter(type,this)
    }

    override fun initUI() {
        rv_view.initRecyclerView()
        sr_layout.onDefaultSmartRefreshLayout()
        sr_layout.setOnRefreshListener{
            mPresenter.refreshData()
        }
        sr_layout.setOnLoadmoreListener{
            mPresenter.fetchMore()
        }
    }

    override fun initData() {
        mAdapter = RepaymentGroupAdapter(null)
        rv_view.adapter = mAdapter
        mAdapter.setOnChildClickListener { itemView, groupPosition, childPosition ->
            RepaymentDetailActivity.startAct(context!!,0,
                    mPresenter.getData()[groupPosition].repaymentItem[childPosition].id)
        }

        tv_all_money.text = type.toString()
        mPresenter.fetch()
    }

    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun updateUI() {
//        tv_all_money.text = mPresenter.getData().money
        if (sr_layout.isRefreshing){
            sr_layout.finishRefresh(500)
        }
        if (sr_layout.isLoading) {
            sr_layout.finishLoadmore(500)
        }
        mAdapter.update(mPresenter.getData())
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