package com.boxin.financial.ui.my.capital.flow.repayment

import android.content.Context
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.setTabLayoutIndicatorWidth
import com.boxin.financial.ui.common.adapter.MyAdapter
import com.boxin.financial.ui.my.capital.flow.recharge.withdraw.CapitalFlowRWActivity
import com.boxin.financial.ui.my.capital.flow.repayment.list.RepaymentListFragment
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_repayment.*
import kotlinx.android.synthetic.main.head_tab.*

/**
 * 回款流水，包含待回款和已回款
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class RepaymentActivity :
        BasePActivity<RepaymentContract.Presenter<String>>(R.layout.activity_repayment),
        RepaymentContract.View {

    private lateinit var adapter: MyAdapter
    private val titles = ArrayList<String>()
    private val mTabCount = 2
    val mFragment = ArrayList<Fragment>()

    companion object {
        val MOVABLE_COUNT = 4
        fun startAct(context: Context){
                    context.startActivity(Intent(context,RepaymentActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = RepaymentPresenter(this)
    }

    override fun initUI() {
        iv_left_img.visibility = View.VISIBLE
        titles.add("待回款")
        titles.add("已回款")

        mFragment.add(RepaymentListFragment(0))
        mFragment.add(RepaymentListFragment(1))

        onClickBind(this,iv_left_img)
    }

    override fun initData() {
        adapter = MyAdapter(supportFragmentManager,titles,mFragment)
        vp_fm.adapter = adapter
        initTab()
    }

    private fun initTab(){
        tl_tab.tabMode = if (mTabCount <= CapitalFlowRWActivity.MOVABLE_COUNT) TabLayout.MODE_FIXED else TabLayout.MODE_SCROLLABLE
        tl_tab.post({
            tl_tab.setTabLayoutIndicatorWidth(25,25)
        })
        tl_tab.setupWithViewPager(vp_fm)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> toExit(true)
        }
    }

    override fun updateUI() {
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