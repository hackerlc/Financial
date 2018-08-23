package com.boxin.financial.ui.my.capital.flow.recharge.withdraw.list

import android.annotation.SuppressLint
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_rw_list.*

@SuppressLint("ValidFragment")
/**
 * 充值提现列表
 * @author joker
 * Email:812405389@qq.com
 * @version
 *
 * @param type 0 充值 1提现
 */
class RWListFragment(val type:Int) :
        BasePFragment<RWListContract.Presenter<String>>(R.layout.fragment_rw_list),
        RWListContract.View {

    override fun attachPresenter() {
        mPresenter = RWListPresenter(this)
    }

    override fun initUI() {

    }

    override fun initData() {
        tv_test.text = type.toString()
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