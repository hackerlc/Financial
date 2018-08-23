package com.boxin.financial.ui.investment

import android.content.Context
import android.content.Intent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.APIConfig
import com.boxin.financial.entity.InvestmentRecordInfo
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.investment.pan.InvestmentPanActivity
import com.boxin.financial.ui.product.ProductDetailActivity
import com.boxin.financial.ui.web.WebActivity
import com.boxin.financial.ui.web.WebFragment
import com.boxin.financial.utils.CommonUtils
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_investment.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 投资详情
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class InvestmentActivity :
        BasePActivity<InvestmentContract.Presenter<InvestmentRecordInfo>>(R.layout.activity_investment),
        InvestmentContract.View {

    companion object {
        fun startAct(context: Context,id: String){
                    context.startActivity(Intent(context,InvestmentActivity::class.java)
                            .putExtra("id",id))
        }
    }

    lateinit var mId: String

    override fun attachPresenter() {
        mId = intent.getStringExtra("id")
        if (mId.isNullOrEmpty()){
            showToast("找不到投资记录详情")
            toExit(true)
        }
        mPresenter = InvestmentPresenter(mId,this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"投资详情",iv_left_img)

        onClickBind(this, iv_left_img,tv_detail,tv_web,tv_web2)
    }

    override fun initData() {
        mPresenter.fetch()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {toExit(true)}
            R.id.tv_detail -> {WebActivity.startAct(this,APIConfig.fetchHtmlUrl(APIConfig.HTML_URL_PRODUCT_DETAIL, 0,mId),"")}
            R.id.tv_web -> {WebActivity.startAct(this,APIConfig.fetchHtmlUrl(APIConfig.HTML_URL_PRODUCT_DETAIL, 0,mId),"")}
            R.id.tv_web2 -> {WebActivity.startAct(this,APIConfig.fetchHtmlUrl(APIConfig.HTML_URL_PRODUCT_DETAIL, 0,mId),"")}
        }
    }

    override fun updateUI() {
        tv_title_info.text = mPresenter.getData().title
        tv_type.text = mPresenter.getData().type?.text
        tv_num.text = mPresenter.getData().orderCode
        tv_date.text = mPresenter.getData().date
        tv_no.text = mPresenter.getData().productCode
        tv_interest_rate.text = "${CommonUtils.onFormatTwo(mPresenter.getData().proRate)}%"
        tv_time.text = "${mPresenter.getData().timeLimit+mPresenter.getData().interestTypeStr}"
        tv_style.text = mPresenter.getData().refundType?.text
        tv_money.text= "${CommonUtils.onFormatTwo(mPresenter.getData().money)}元"
        tv_interest.text = "${CommonUtils.onFormatTwo(mPresenter.getData().interest)}元"
        tv_start_time.text = mPresenter.getData().startDate
        if ("flush".equals(mPresenter.getData().type?.name)){
            ll_end.visibility = View.VISIBLE
            rl_web.visibility = View.VISIBLE
            tv_end_time.text = mPresenter.getData().endDate
        } else {
            ll_end.visibility = View.GONE
            rl_web.visibility = View.GONE
        }
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