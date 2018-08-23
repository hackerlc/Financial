package com.boxin.financial.ui.web

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.entity.UpApp
import com.boxin.financial.entity.User
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.trello.rxlifecycle2.LifecycleTransformer
import gear.yc.com.gearlibrary.utils.ProgressDialogUtil
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.head_common.*

/**
 * 查看网页
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class WebActivity :
        BasePActivity<WebContract.Presenter<User>>(R.layout.activity_web),
        WebContract.View {
    private var mUrl: String? = null
    private var mTitle: String? = null
    private var mType: Int = 0

    private lateinit var mFragment: WebFragment

    companion object {
        /**
         * @param type 0默认 1加载html代码
         */
        fun startAct(context: Context, url: String, title: String?, type: Int? = 0) {
            context.startActivity(Intent(context, WebActivity::class.java)
                    .putExtra("url",url)
                    .putExtra("type",type)
                    .putExtra("title",title))
        }
    }

    override fun attachPresenter() {
        mPresenter = WebPresenter(this)
    }
    override fun initUI() {
        mUrl = intent.getStringExtra("url")
        mTitle = intent.getStringExtra("title")
        mType = intent.getIntExtra("type",0)
        if (mUrl.isNullOrEmpty()){
            ToastUtil.getInstance().makeShortToast(this,"无连接")
            toEnter(true)
        }
        if (mTitle.isNullOrEmpty()) {
            mTitle = ""
        }
        onHeadChange(head_top,tv_title,mTitle!!,iv_left_img)
        mFragment = WebFragment(mUrl!!,mTitle,mType)
        mFragment.setOnChangeTitleListener(object : WebFragment.onChangeTitleListener{
            override fun changeTitle(title: String?) {
                tv_title.text = if (title.isNullOrEmpty()) mTitle else title
            }
        })
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.rl_fm,mFragment).commit()

        onClickBind(this, iv_left_img)
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.close()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {
                toExit(true)
            }
        }
    }

    override fun updateUI() {
        tv_title.text = mTitle
    }

    override fun upDataApp(data: UpApp) {

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

    /**
     * 在这里可以拦截返回事件并处理它们
     * 例如当你需要点击2次返回按钮退出应用的时候
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mType ==0){
                //后退
                if(!mFragment.goBack()){
                    toExit(true)
                }
                return true
            } else {
                toExit(true)
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun <R> getLifecycle2(): LifecycleTransformer<R> {
        return bindToLifecycle()
    }

    override fun <R> getLifecycleDestroy(): LifecycleTransformer<R> {
        return bindToLifecycleDestroy()
    }
}