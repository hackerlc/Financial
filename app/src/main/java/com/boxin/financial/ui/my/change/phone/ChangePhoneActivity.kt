package com.boxin.financial.ui.my.change.phone

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.my.change.phone.code.ChangeOldPhoneFragment
import com.boxin.financial.ui.my.change.phone.psd.ChangePsdPhoneFragment
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_change_phone.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 修改手机号
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ChangePhoneActivity :
        BasePActivity<ChangePhoneContract.Presenter<String>>(R.layout.activity_change_phone),
        ChangePhoneContract.View {

    val fragments = ArrayList<Fragment>()
    var isCodePhone = true

    companion object {
        fun startAct(context: Context){
            context.startActivity(Intent(context, ChangePhoneActivity::class.java))
        }
    }

    override fun attachPresenter() {
        mPresenter = ChangePhonePresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"修改手机号",iv_left_img)

        fragments.add(ChangeOldPhoneFragment())
        fragments.add(ChangePsdPhoneFragment())

        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.rl_fm,fragments[0])
        ft.add(R.id.rl_fm,fragments[1])
        ft.hide(fragments[1])
        ft.commit()

        onClickBind(this,tv_pwd_phone,iv_left_img)
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_pwd_phone ->{
                val ft = supportFragmentManager.beginTransaction()
                ft.hide(fragments[0])
                ft.show(fragments[1])
                ft.commit()
                isCodePhone = false
                tv_pwd_phone.visibility = View.INVISIBLE
            }
            R.id.iv_left_img -> {
                if (isCodePhone){
                    toExit(true)
                } else {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.hide(fragments[1])
                    ft.show(fragments[0])
                    ft.commit()
                    isCodePhone = true
                    tv_pwd_phone.visibility = View.VISIBLE
                }
            }
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