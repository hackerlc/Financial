package com.boxin.financial.ui.success

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.base.glide.AGlide
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.ui.main.MainActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_success.*

/**
 * 各种成功页面
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class SuccessActivity :
        BasePActivity<SuccessContract.Presenter<String>>(R.layout.activity_success),
        SuccessContract.View {

    var mType = 0

    //资源

    var img = R.mipmap.ic_launcher
    var str = "支付成功"
    var btnStr = "返回首页"

    companion object {
        /**
         * @param type 0 支付成功  返回首页
         */
        fun startAct(context: Context, type: Int){
                    context.startActivity(Intent(context,SuccessActivity::class.java)
                            .putExtra("type",type))
        }
    }

    override fun attachPresenter() {
        mPresenter = SuccessPresenter(this)
    }

    override fun initUI() {
        mType = intent.getIntExtra("type", 0)
        updateUI()
        onClickBind(this, btn_success)
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_success -> {
                successClick()
            }
        }
    }

    private fun successClick(){
        when (mType) {
            0 -> {
                MainActivity.startAct(this,1)
            }
        }
    }

    override fun updateUI() {
        when (mType){
            0 -> {
                img = R.mipmap.ic_launcher
                str = "支付成功"
                btnStr = "返回首页"
            }
        }

        AGlide.with(this)
                .load(img)
                .into(iv_success)
        tv_success.text = str
        btn_success.text = btnStr
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0){
            successClick()
        }
        return super.onKeyDown(keyCode, event)
    }
}