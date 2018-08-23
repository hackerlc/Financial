package com.boxin.financial.ui.home

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.entity.HomeData
import com.boxin.financial.entity.HomeDetail
import com.boxin.financial.ui.financial.detail.FinancialDetailActivity
import com.boxin.financial.ui.financial.list.FinancialAdapter
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.ui.custom.GearRecyclerItemDecoration
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.head_home_banner.view.*

/**
 * 首页
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class HomeFragment :
        BasePFragment<HomeContract.Presenter<HomeData>>(R.layout.fragment_home),
        HomeContract.View {
    lateinit var adapter: FinancialAdapter
    lateinit var mHomeHeadViewHolder: HomeHeadViewHolder

    override fun attachPresenter() {
        mPresenter = HomePresenter(this)
    }

    override fun initUI() {
        rv_data.layoutManager = LinearLayoutManager(context!!)
        rv_data.addItemDecoration(GearRecyclerItemDecoration(context,LinearLayoutManager.VERTICAL,10))

        val headView = (activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater)
                .inflate(R.layout.head_home_banner, rv_data, false)
        mHomeHeadViewHolder = HomeHeadViewHolder(headView, mPresenter.getBanner())

        adapter = FinancialAdapter(mPresenter.getData().financials)
        adapter.setOnItemClickListener { adapter, view, position ->
            FinancialDetailActivity.startAct(context!!,mPresenter.getData().financials[position].id!!)
        }
        rv_data.adapter = adapter
        adapter.setHeaderView(headView)

        sr_layout.setColorSchemeResources(R.color.colorPrimary)
        sr_layout.setOnRefreshListener {
            mPresenter.refreshData()
        }
    }

    override fun initData() {
        mPresenter.fetch()
    }

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    override fun onResume() {
        super.onResume()
        mHomeHeadViewHolder.view.br_view.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        mHomeHeadViewHolder.view.br_view.stopAutoPlay()
        sr_layout.isRefreshing = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun updateUI() {
        sr_layout.isRefreshing = false
        mHomeHeadViewHolder.loadBanner(mPresenter.getBanner())

        mHomeHeadViewHolder.upHeadView(mPresenter.getData(),mPresenter.getNews())

        adapter.upData(mPresenter.getData().financials)
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