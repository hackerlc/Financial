package com.boxin.financial.ui.web

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.webkit.*
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.config.AppConfig
import com.boxin.financial.ui.financial.more.FinancialMoreContract
import com.boxin.financial.ui.login.LoginActivity
import com.boxin.financial.ui.login.lock.GestureActivity
import com.boxin.financial.ui.login.lock.GestureCreateActivity
import com.boxin.financial.ui.my.setting.SettingActivity
import com.boxin.financial.utils.SPUtil
import com.google.gson.Gson
import com.tbruyelle.rxpermissions2.RxPermissions
import gear.yc.com.gearlibrary.utils.ToastUtil
import gear.yc.com.gearlibrary.utils.web.BaseWeb
import kotlinx.android.synthetic.main.fragment_web_scroll_old.*


@SuppressLint("ValidFragment")
/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/10
 * @param mType 加载类型 0普通 1加载html代码
 * @param isScroll 是否外层有嵌套
 */
class WebH5OldFragment(var mUrl: String, var mTitle: String?, var mType: Int?, var isScroll:Boolean = false) :
        BasePFragment<FinancialMoreContract.Presenter<String>>(if (isScroll) R.layout.fragment_web_scroll_old else R.layout.fragment_web) {

    /**
     * WebView基础设置
     */
    private val baseWebView = BaseWeb()
    /**
     * 缓存url
     */
    private var cacheUrl = ""
    /**
     * webView设置
     */
    var settings: WebSettings? = null


    var mCacheTitle: String? = ""
    private val mWebClient = object : WebViewClient() {
        @SuppressLint("MissingPermission")
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val sUrl = request.url.toString()
            Log.i("WEB_URL:",sUrl)
            if (sUrl.contains("tel")) {
                val rxPermissions = RxPermissions(activity!!)
                rxPermissions.request(Manifest.permission.CALL_PHONE)
                        .subscribe{
                            it ->
                            if (it){
                                telPhone(sUrl)
                            } else {
                                ToastUtil.getInstance().makeShortToast(context,"需要授权才能拨打电话")
                            }
                        }
                return true
            }
            if (Build.VERSION.SDK_INT < 26) {
                view.loadUrl(sUrl)
                return true
            }
            return false
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            handler?.proceed()
        }

        override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
//            cacheUrl = url
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            if (settings != null) {
                if(!settings!!.loadsImagesAutomatically) {
                    settings!!.loadsImagesAutomatically = true
                }
            }
//            web_data.visibility = View.VISIBLE
            super.onPageFinished(view, url)
        }
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            mCacheTitle = title
            cacheUrl = view!!.url.toString()
            if (mListener != null) {
                if (mTitle.isNullOrEmpty()) {
                    mListener!!.changeTitle(mCacheTitle)
                } else {
                    mListener!!.changeTitle(mTitle)
                }
            }
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress >= 100){
                cacheUrl = view!!.url.toString()
                Log.i("onProgressUrl","${cacheUrl}||${newProgress}")
                if (mListener != null) {
                    if (mTitle.isNullOrEmpty()) {
                        mListener!!.changeTitle(mCacheTitle)
                    } else {
                        mListener!!.changeTitle(mTitle)
                    }
                }
            }
        }

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            return true
        }

        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult): Boolean {
            val b = AlertDialog.Builder(context)
            b.setTitle("Alert")
            b.setMessage(message)
            b.setPositiveButton(android.R.string.ok, { dialog, which -> result.confirm() })
            b.setCancelable(false)
            b.create().show()
            return true
        }
    }
    private var mListener: onChangeTitleListener? = null

    fun setOnChangeTitleListener(listener: onChangeTitleListener){
        mListener = listener
    }

    override fun attachPresenter() {
    }

    override fun initUI() {
        web_data.webViewClient = mWebClient
        web_data.webChromeClient = webChromeClient
        web_data.addJavascriptInterface(AndroidtoJs(),"android")
        baseWebView.settings = web_data.settings
        settings = baseWebView.setWeb(context)

    }

    override fun initData() {
        cacheUrl = mUrl
        when(mType){
            0 -> web_data.loadUrl(mUrl)
            1 -> web_data.loadDataWithBaseURL(null,mUrl,"text/html","utf-8",null)
        }
    }

    override fun onResume() {
        super.onResume()
        web_data.resumeTimers()
        web_data.onResume()
    }

    override fun onPause() {
        super.onPause()
        web_data.onPause()
        web_data.pauseTimers()
    }

    override fun onDestroy() {
        if (web_data != null) {
            web_data.destroy()
        }
        super.onDestroy()
    }

    private fun telPhone(sUrl: String){
        startActivity(Intent(Intent.ACTION_DIAL,Uri.parse(sUrl)))
    }

    fun onClearCache(){
        baseWebView.clearWebViewCache(context!!)
    }

    fun updateUI(){
        Log.i("loadUrl","${web_data.url}")
//        web_data.loadUrl(web_data.url)
//        onClearCache()
        updateWebLoginFlag()
//        updateWebCache() // IOS好像不支持就不能这样调了
        web_data.reload()
//        if (AppConfig.IS_LOGIN){
//            web_data.loadUrl("${web_data.url}&token=${AppConfig.I_USER?.token}")
//        } else {
//            web_data.reload()
//        }
    }

    fun goBack(): Boolean{
        return if (web_data.canGoBack()){
            web_data.goBack()
            true
        } else {
            false
        }
    }

    fun onCacheUrl(): String{
        return cacheUrl
    }

    fun onCacheTitle(): String{
        return if (mCacheTitle.isNullOrEmpty()){
            ""
        } else {
            mCacheTitle!!
        }
    }

    override fun onClick(v: View?) {
    }

    fun updateWebLoginFlag(){
        Log.i("CLEAR_web_cache","clearLocalStorage()")
        web_data.post({
            web_data.evaluateJavascript("javascript:clearLocalStorage()", ValueCallback {

            })
        })
    }

    fun updateWebCache(){
        val gson = Gson()
        if (AppConfig.IS_LOGIN) {
            web_data.post({
                web_data.evaluateJavascript("javascript:appDealUserInfo('${gson.toJson(AppConfig.I_USER)}')", ValueCallback {

                })
            })
        }
    }

    interface onChangeTitleListener {
        fun changeTitle(title: String?)
    }

    inner class AndroidtoJs : Any() {
        //        @JavascriptInterface
//        fun jump2NewWeb() {
//            LoginActivity.startAct(context!!)
//        }
        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        fun jump2Login() {
            LoginActivity.startAct(context!!)
        }

        @JavascriptInterface
        fun jump2Setting() {
            SettingActivity.startAct(context!!)
        }

        /**
         * 开关手势
         */
        @JavascriptInterface
        fun jump2SignLogin(isLock: Boolean) {
            if (isLock) {
                GestureCreateActivity.startActivity(context!!)
            } else {
                GestureActivity.startAct(context!!,"setting")
            }
        }

        /**
         * 退出登录
         */
        @JavascriptInterface
        fun exitLogin(){
            AppConfig.IS_LOGIN = false
            SPUtil.setLogin(AppConfig.IS_LOGIN)
            web_data.post({
                web_data.goBack()
            })
//            web_data.goBack()
            LoginActivity.startAct(context!!)
        }

        @JavascriptInterface
        fun getUserInfo(method: String) {
            Log.i("METHOD","${method}||${AppConfig.IS_LOGIN}")
            if (AppConfig.IS_LOGIN){
                val gson = Gson()
                Log.i("JSON",gson.toJson(AppConfig.I_USER))
                web_data.post({
                    web_data.evaluateJavascript("javascript:${method}('${gson.toJson(AppConfig.I_USER)}')", ValueCallback {

                    })
                })
            } else {
                web_data.post({
                    web_data.evaluateJavascript("javascript:${method}('')", ValueCallback {

                    })
                })
            }
        }
    }
}