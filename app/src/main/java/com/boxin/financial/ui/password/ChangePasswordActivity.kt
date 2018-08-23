package com.boxin.financial.ui.password

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.password.fragment.ChangePasswordFragment
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.head_common.*

/**
 * 密码修改
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class ChangePasswordActivity :
        BasePActivity<ChangePasswordContract.Presenter<String>>(R.layout.activity_changepassword),
        ChangePasswordContract.View {

    lateinit var mFragment :Fragment
    var type = 0

    companion object {
        /**
         * @param type 0 修改密码 1 忘记密码
         */
        fun startAct(context: Context,type: Int){
                    context.startActivity(Intent(context,ChangePasswordActivity::class.java)
                            .putExtra("type",type))
        }
    }

    override fun attachPresenter() {
        mPresenter = ChangePasswordPresenter(this)
    }

    override fun initUI() {
        type = intent.getIntExtra("type",0)
        var titleStr = ""
        when(type){
            0 -> {titleStr = "修改密码"}
            1 -> titleStr = "忘记密码"
        }
        onHeadChange(head_top,tv_title,titleStr,iv_left_img)
        mFragment = ChangePasswordFragment(type)
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.rl_fm,mFragment).commit()

        onClickBind(this,iv_left_img)
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {finish()}
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