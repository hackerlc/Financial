package com.boxin.financial.ui.financial.main

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.common.adapter.MyTabAdapter
import com.boxin.financial.ui.financial.list.FinancialListFragment
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_financial.*

/**
 * 标的
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialFragment :
        BasePFragment<FinancialContract.Presenter<String>>(R.layout.fragment_financial),
        FinancialContract.View {

    val mFragment = FinancialListFragment(1)
    val mFragmentAll = FinancialListFragment(0)

    var mFragments: MutableList<Fragment> = ArrayList()
    lateinit var mAdapter: MyTabAdapter

    override fun attachPresenter() {
        mPresenter = FinancialPresenter(this)
    }

    override fun initUI() {
        mFragments.add(mFragment)
        mFragments.add(mFragmentAll)
        mAdapter = MyTabAdapter(childFragmentManager,mFragments)
        vp_view.adapter = mAdapter
        vp_view.currentItem = 0
        vp_view.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0){
                    btn_tab_1.isSelected = true
                    btn_tab_2.isSelected = false
                } else {
                    btn_tab_1.isSelected = false
                    btn_tab_2.isSelected = true
                }
            }

        })

        btn_tab_1.isSelected = true
        btn_tab_2.isSelected = false
        onClickBind(this, rl_no_data, btn_tab_1,btn_tab_2)
    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rl_no_data -> {
                updateUI()
            }
            R.id.btn_tab_1 -> {
                if (!btn_tab_1.isSelected){
                    btn_tab_1.isSelected = true
                    btn_tab_2.isSelected = false
                    vp_view.currentItem = 0
                }
            }
            R.id.btn_tab_2 -> {
                if (!btn_tab_2.isSelected){
                    btn_tab_2.isSelected = true
                    btn_tab_1.isSelected = false
                    vp_view.currentItem = 1
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun updateUI() {
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