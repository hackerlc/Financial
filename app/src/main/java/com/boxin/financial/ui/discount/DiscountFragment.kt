package com.boxin.financial.ui.discount

import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil

/**
 * 折扣
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class DiscountFragment :
        BasePFragment<DiscountContract.Presenter<String>>(R.layout.fragment_discount),
        DiscountContract.View {

    override fun attachPresenter() {
        mPresenter = DiscountPresenter(this)
    }

    override fun initUI() {
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
        TODO("not implemented") //To change FieldMap of created functions use File | Settings | File Templates.
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