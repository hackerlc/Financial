package com.boxin.financial.ui.financial.more

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.config.APIConfig
import com.boxin.financial.ui.common.adapter.MyAdapter
import com.boxin.financial.ui.my.main.MyFragment
import com.boxin.financial.ui.web.WebFragment
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_financial_more.*

@SuppressLint("ValidFragment")
/**
 * 标的详情更多信息
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class FinancialMoreFragment(var mId: String) :
        BasePFragment<FinancialMoreContract.Presenter<String>>(R.layout.fragment_financial_more),
        FinancialMoreContract.View {

    val mTitle = ArrayList<String>()
    val mFragment = ArrayList<Fragment>()
    lateinit var mAdapter: MyAdapter

    override fun attachPresenter() {
        mPresenter = FinancialMorePresenter(this)
    }

    override fun initUI() {
        mTitle.add("安全保障")
        mTitle.add("项目信息")
        mTitle.add("投资记录")
        mFragment.add(WebFragment(APIConfig.fetchHtmlUrl(APIConfig.HTML_URL_PRODUCT_SAFETY, 0,mId),"",0,true))
        mFragment.add(WebFragment(APIConfig.fetchHtmlUrl(APIConfig.HTML_URL_PRODUCT_DETAIL, 0,mId),"",0,true))
        mFragment.add(WebFragment(APIConfig.fetchHtmlUrl(APIConfig.HTML_URL_PRODUCT_RECORD, 0,mId),"",0,true))

        mAdapter = MyAdapter(childFragmentManager, mTitle, mFragment)
        vp_div.adapter = mAdapter
        tl_tab.setupWithViewPager(vp_div)
    }

    override fun initData() {
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