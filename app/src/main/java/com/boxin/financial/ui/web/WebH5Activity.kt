package com.boxin.financial.ui.web

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.KeyEvent
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.activity.BasePActivity
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.entity.UpApp
import com.boxin.financial.entity.User
import com.boxin.financial.entity.WebShared
import com.boxin.financial.extended.onClickBind
import com.boxin.financial.extended.onHeadChange
import com.boxin.financial.ui.guide.GuideActivity
import com.boxin.financial.ui.login.lock.GestureActivity
import com.boxin.financial.utils.CommonUtils
import com.boxin.financial.utils.SPUtil
import com.boxin.financial.view.dialog.LoadingDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.LifecycleTransformer
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import gear.yc.com.gearlibrary.rxjava.rxbus.RxBus
import gear.yc.com.gearlibrary.rxjava.rxbus.annotation.Subscribe
import gear.yc.com.gearlibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_web_h5.*
import kotlinx.android.synthetic.main.head_common.*
import util.UpdateAppUtils


/**
 * 查看网页
 * @author joker
 * Email:812405389@qq.com
 * @version
 */
class WebH5Activity :
        BasePActivity<WebContract.Presenter<User>>(R.layout.activity_web_h5),
        WebContract.View {
    private var mUrl: String? = null
    private var mTitle: String = "首页"
    private var mType: Int = 0

    private lateinit var mFragment: WebH5Fragment

    companion object {
        var isLoad = false
        @SuppressLint("StaticFieldLeak")
        lateinit var mLoadingDialog: LoadingDialog
    }

    override fun attachPresenter() {
        RxBus.getInstance().register(this)
        mPresenter = WebPresenter(this)
    }

    override fun initUI() {
        mUrl = "${APIConfig.H5}"
        mType = intent.getIntExtra("type", 0)
        onHeadChange(head_top,tv_title,mTitle,iv_left_img)
        mLoadingDialog = LoadingDialog(this)
        mFragment = WebH5Fragment(mUrl!!, "", mType)
        mFragment.setOnChangeTitleListener(object : WebH5Fragment.onChangeTitleListener{
            override fun changeTitle(title: String?) {
                tv_title.text = if (title.isNullOrEmpty()) mTitle else title
                if (mFragment.onCacheUrl().indexOf("head=false") != -1){
                    disTitleView()
                } else {
                    showTitleView()
                }
//                if (title!!.indexOf("首页",0) != -1){
//                    disTitleView()
//                } else {
//                    if (mFragment.onCacheUrl().indexOf("head=false") != -1){
//                        disTitleView()
//                    } else {
//                        showTitleView()
//                    }
//                }
            }
        })
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.rl_fm, mFragment).commit()

        onClickBind(this,iv_left_img)
    }

    override fun initData() {
        onDialog(true)
//        updateWebLoginFlag()
        if (SPUtil.isFirst(this)) {
            GuideActivity.startAct(this)
            iv_welcome.visibility = View.GONE
        } else {
            iv_welcome.visibility = View.GONE
            //判断是否显示解锁
            AppConfig.IS_LOGIN = SPUtil.isLogin()
            if (AppConfig.IS_LOGIN){
                AppConfig.I_USER = SPUtil.readObj(SPUtil.USER_PSD) as User
                if (AppConfig.I_USER!!.signLock){
                    if (SPUtil.getShoushiLock(AppConfig.I_USER?.phone!!).isNotEmpty()){
                        GestureActivity.startAct(this,"main")
                    } else {
                        //快速登录
                        AppConfig.I_USER!!.signLock = false
                        mPresenter.fetch()
                    }
                } else {
                    //快速登录
                    mPresenter.fetch()
                }
            }
        }
        SPUtil.setFirst(this)
//        Observable.timer(3000L, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(bindToLifecycleDestroy())
//                .subscribe({
//                })
        //更新判断
//        val upApp = UpApp()
//        upApp.isForce = false
//        upApp.url = "https://appdownload.zhecaijinfu.com/zcb-release-1.apk"
//        upApp.versionNum = 251
//        upApp.version = "V2.5.1"
//        upDataApp(upApp)
    }



    override fun onRestart() {
        super.onRestart()
        if (isLoad) {
            isLoad = false
            onDialog(true)
            mFragment.updateUI()
        }
    }

    override fun onDestroy() {
        RxBus.getInstance().unRegister(this)
        super.onDestroy()
        mPresenter.close()
    }

    fun showTitleView(){
        ic_head.visibility = View.VISIBLE
    }

    fun disTitleView(){
        ic_head.visibility = View.GONE
    }

    fun updateWebLoginFlag(){
        mFragment.updateWebLoginFlag()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_left_img -> {
                onKeyDown(KeyEvent.KEYCODE_BACK,KeyEvent(0,0))
            }
        }
    }

    override fun updateUI() {
        mFragment.updateUI()
    }

    override fun upDataApp(data: UpApp) {
        //显示权限管理
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe{
                    it ->
                    if (it){
                        UpdateAppUtils.from(this)
                                .serverVersionCode(data.versionNum)  //服务器versionCode
                                .serverVersionName(data.version) //服务器versionName
                                .apkPath(data.url) //最新apk下载地址
                                .isForce(data.isForce)
                                .update()
                    } else {
                        showToast("需要授权才能更新app")
                    }
                }
    }

    override fun showToast(str: String) {
        ToastUtil.getInstance().makeShortToast(this, str)
    }

    override fun onError(error: Throwable) {

    }

    override fun onDialog(show: Boolean) {
        if (show) {
            CommonUtils.showLoading(mLoadingDialog)
        } else {
            CommonUtils.hideLoading(mLoadingDialog)
        }
    }

    /*分享*/
    @Subscribe(tag = RxBus.TAG_OTHER)
    fun onShared(shared: WebShared){
        onShared(shared.url,shared.title,shared.des)
    }

    fun onShared(url: String?, title: String?, des: String?){
        Log.i("SHARD","$url || $title || $des")
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe { it ->
                    if (it) {
                        val thumb = UMImage(this, R.mipmap.ic_launcher)
                        val web = UMWeb(url)
                        web.title = title//标题
                        web.description = des//描述
                        web.setThumb(thumb)
                        ShareAction(this)
                                .withMedia(web)
                                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.MORE)
                                .open()
                    } else {
                        showToast("需要授权才能分享")
                    }
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    private val shareListener = object :UMShareListener {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        override fun onStart(platform: SHARE_MEDIA?) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        override fun onResult(platform: SHARE_MEDIA?) {
            showToast("分享成功")
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        override fun onError(platform: SHARE_MEDIA? , t: Throwable?) {
            showToast("分享失败${t?.message}")
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        override fun onCancel(platform: SHARE_MEDIA?) {
            showToast("取消分享")
        }
    }

    /**
     * 在这里可以拦截返回事件并处理它们
     * 例如当你需要点击2次返回按钮退出应用的时候
     */
    var oldTime = 0L

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
//            if (mFragment.onCacheTitle().indexOf("首页",0) != -1){
//                if ((System.currentTimeMillis() - oldTime) > 2000) {
//                    ToastUtil.getInstance().makeLongToast(this, "再按一次退出程序")
//                    oldTime = System.currentTimeMillis()
//                } else {
//                    exitApp()
//                }
//                return true
//            }
//            mFragment.onCacheUrl().indexOf("#/productList",0) != -1 ||
            if (mFragment.onCacheUrl().indexOf("head=false",0) != -1){
                if ((System.currentTimeMillis() - oldTime) > 2000) {
                    ToastUtil.getInstance().makeLongToast(this, "再按一次退出程序")
                    oldTime = System.currentTimeMillis()
                } else {
                    exitApp()
                }
                return true
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && mType == 0) {
                //后退
                if (!mFragment.goBack()) {
                    if ((System.currentTimeMillis() - oldTime) > 2000) {
                        ToastUtil.getInstance().makeLongToast(this, "再按一次退出程序")
                        oldTime = System.currentTimeMillis()
                    } else {
                        exitApp()
                    }
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