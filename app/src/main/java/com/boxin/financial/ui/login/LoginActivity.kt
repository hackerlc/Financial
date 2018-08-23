package com.boxin.financial.ui.login

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.AppConfig
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.common.adapter.MyAdapter
import com.boxin.financial.ui.login.fragment.LoginFastFragment
import com.boxin.financial.ui.login.fragment.LoginPsdFragment
import com.boxin.financial.ui.web.WebH5Activity
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.head_common.*

/**
 * 登录
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class LoginActivity :
        BasePActivity<LoginContract.Presenter<String>>(R.layout.activity_login),
        LoginContract.View{

    val mTitle: MutableList<String> = ArrayList()
    val mFragment: MutableList<Fragment> = ArrayList()

    companion object {
        /**
         * @param type 类型 0 密码登录 1 快速登录 3 H5登录
         */
        fun startAct(context: Context, type: Int = 0) {
            startAct(context, type, null)
        }

        /**
         * @param type 类型 0 密码登录 1 快速登录
         */
        fun startAct(context: Context, type: Int, phone: String?) {
            context.startActivity(Intent(context, LoginActivity::class.java)
                    .putExtra("type", type)
                    .putExtra("phone", phone))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)
    }

    override fun attachPresenter() {
        mPresenter = LoginPresenter(this)
    }

    override fun initUI() {
        onHeadChange(head_top,tv_title,"登录",iv_left_img)
        mTitle.add("密码登录")
        mTitle.add("快速登录")
        mFragment.add(LoginPsdFragment())
        mFragment.add(LoginFastFragment())

        val myAdapter = MyAdapter(supportFragmentManager, mTitle, mFragment)
        vp_container.adapter = myAdapter
        tablatyou_attention1.setxTabDisplayNum(2)
        tablatyou_attention1.setupWithViewPager(vp_container)
        tablatyou_attention1.setTabsFromPagerAdapter(myAdapter)

        onClickBind(this,iv_left_img)
    }

    override fun initData() {
    }

    var defaultValue = 0
    override fun onRestart() {
        super.onRestart()
        if (AppConfig.IS_LOGIN){
            finish()
        }
        defaultValue = intent.getIntExtra("type", 0)
        if (defaultValue == 1) {
            vp_container.currentItem = defaultValue
            //快速登录  填入手机号并发送验证码
            val phone = intent.getStringExtra("phone")
            onFast(phone)
        }
    }

    override fun onStop() {
        super.onStop()
        intent.putExtra("type",vp_container.currentItem)
    }

    override fun onDestroy() {
        super.onDestroy()
        WebH5Activity.isLoad = true
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> toExit(true)
        }
    }

    /**
     * 跳转到fast页面
     */
    fun onFast(phone: String?) {
        if (!phone.isNullOrEmpty()) {
            (mFragment[1] as LoginFastFragment).fastLogin(phone!!)
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