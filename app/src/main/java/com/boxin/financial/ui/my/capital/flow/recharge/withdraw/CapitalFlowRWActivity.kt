package com.boxin.financial.ui.my.capital.flow.recharge.withdraw

import android.content.Context
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.common.adapter.MyTabAdapter
import com.boxin.financial.ui.my.capital.flow.recharge.withdraw.list.RWListFragment
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ConvertPadPlus
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_capital_flow_rw.*
import kotlinx.android.synthetic.main.head_tab.*

/**
 * 充值提现页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class CapitalFlowRWActivity :
        BasePActivity<CapitalFlowRWContract.Presenter<String>>(R.layout.activity_capital_flow_rw),
        CapitalFlowRWContract.View {

    private lateinit var adapter: MyTabAdapter
    private val titles = ArrayList<String>()
    private val mTabCount = 2
    val mFragment = ArrayList<Fragment>()

    companion object {
        val MOVABLE_COUNT = 4
        fun startAct(context: Context){
                    context.startActivity(Intent(context,CapitalFlowRWActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = CapitalFlowRWPresenter(this)
    }

    override fun initUI() {
        iv_left_img.visibility = View.VISIBLE
        titles.add("充值记录")
        titles.add("提现记录")

        mFragment.add(RWListFragment(0))
        mFragment.add(RWListFragment(1))

        onClickBind(this,iv_left_img)
    }

    override fun initData() {
        adapter = MyTabAdapter(supportFragmentManager,mFragment)
        vp_div.adapter = adapter
        initTab()
    }

    private fun initTab(){
        tl_tab.tabMode = if (mTabCount <= MOVABLE_COUNT) TabLayout.MODE_FIXED else TabLayout.MODE_SCROLLABLE
        tl_tab.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.white))
        tl_tab.setSelectedTabIndicatorHeight(ConvertPadPlus.dip2px(this,1f))
        tl_tab.setupWithViewPager(vp_div)

        for (i in 0 until titles.size) {
            val tab = tl_tab.getTabAt(i)
            val tv = LayoutInflater.from(this).inflate(R.layout.tabview_main, tl_tab, false) as TextView
            tv.text = titles[i]
            tab?.customView = tv
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {
                toExit(true)
            }
        }
    }

    override fun updateUI() {
        TODO("not implemented") //To change FieldMap of created functions use File | Settings | File Templates.
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