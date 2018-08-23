package com.boxin.financial.ui.product

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.base.glide.AGlide
import com.boxin.financial.entity.ProductInfo
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_product_detial.*
import kotlinx.android.synthetic.main.head_common.*
import kotlinx.android.synthetic.main.item_img.view.*
import android.widget.RelativeLayout



/**
 * 产品详情
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ProductDetailActivity :
        BasePActivity<ProductDetailContract.Presenter<ProductInfo>>(R.layout.activity_product_detial),
        ProductDetailContract.View {

    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context,ProductDetailActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = ProductDetailPresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"产品详情",iv_left_img)

        onClickBind(this,iv_left_img)
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
        }
    }

    override fun updateUI() {
        ll_img_div.removeAllViews()
        for (url in mPresenter.getData().imgs!!){
            var imgView = ImageView(this)
            val lp = RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            imgView.layoutParams = lp
            ll_img_div.addView(imgView)
            AGlide.with(this)
                    .load(url)
                    .into(imgView)
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