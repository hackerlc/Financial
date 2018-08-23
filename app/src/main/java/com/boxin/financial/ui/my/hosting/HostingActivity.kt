package com.boxin.financial.ui.my.hosting

import android.content.Context
import android.content.Intent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.AppConfig
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.web.WebActivity
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_hosting.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 托管
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class HostingActivity :
        BasePActivity<HostingContract.Presenter<String>>(R.layout.activity_hosting),
        HostingContract.View {

    companion object {
        fun startAct(context: Context){
                    context.startActivity(Intent(context,HostingActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = HostingPresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"开通托管账户",iv_left_img)
        tv_phone.text = "${AppConfig.I_USER?.phone}"

        onClickBind(this,iv_left_img,btn_ok)
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> toExit(true)
            R.id.btn_ok -> {
                //非空判断
                val name = et_name.text.toString()
                val idCard = et_id_card.text.toString()
                if (name.isEmpty() || idCard.isEmpty()){
                    showToast("名称或身份号不能为空")
                    return
                }
                mPresenter.fetchHosting(et_name.text.toString(),et_id_card.text.toString())
            }
        }
    }

    override fun updateUI() {
        //接口返回html
        WebActivity.startAct(this,mPresenter.getData(),"",1)
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