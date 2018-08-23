package com.boxin.financial.ui.financial.list

import android.annotation.SuppressLint
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.entity.Financial
import com.boxin.financial.extended.initRecyclerView
import com.boxin.financial.extended.onDefaultSmartRefreshLayout
import com.boxin.financial.ui.financial.detail.FinancialDetailActivity
import com.boxin.financial.ui.financial.list.FinancialALLAdapter.onClickMoreListener
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.ui.adapter.GearRecyclerViewAdapter
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_financial_list.*

@SuppressLint("ValidFragment")
/**
 * 投资列表
 * @author joker
 * Email:812405389@qq.com
 * @version
 *
 * @param type 请求类型 0全部 1其他
 */
class FinancialListFragment(val type: Int) :
        BasePFragment<FinancialListContract.Presenter<MutableList<Financial>>>(R.layout.fragment_financial_list),
        FinancialListContract.View, onClickMoreListener {

    lateinit var mAdapter: FinancialALLAdapter

    override fun attachPresenter() {
        mPresenter = FinancialListPresenter(this,type)
    }

    override fun initUI() {
        rv_view.initRecyclerView()
        sr_layout.onDefaultSmartRefreshLayout()
        sr_layout.isEnableLoadmore = false
        sr_layout.setOnRefreshListener{
            mPresenter.refreshData()
        }
    }

    override fun initData() {
        mAdapter = FinancialALLAdapter(context!!,mPresenter.getData())
        rv_view.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : GearRecyclerViewAdapter.OnRecyclerViewItemClickListener<Financial>{
            override fun onItemClick(view: View?, data: Financial) {
                FinancialDetailActivity.startAct(context!!,data.id)
            }
        })
        if (type == 0){
            mAdapter.onClickMore = this
        }
        mPresenter.fetch()
    }

    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    override fun onClickMore() {
        mPresenter.fetchMore()
        mAdapter.onClickMore = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun updateUI() {
        if (sr_layout.isRefreshing){
            sr_layout.finishRefresh(500)
            if (type == 0){
                mAdapter.onClickMore = this
            }
        }
        mAdapter.notifyDataSetChanged()
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