package com.boxin.financial.ui.web

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import com.boxin.financial.R
import com.boxin.financial.base.fragment.BasePFragment
import com.boxin.financial.ui.financial.more.FinancialMoreContract
import com.tencent.smtt.export.external.interfaces.ConsoleMessage
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.boxin.financial.utils.BaseWeb
import kotlinx.android.synthetic.main.fragment_web.*


@SuppressLint("ValidFragment")
/**
 * class info
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/4/10
 * @param mType 加载类型 0普通 1加载html代码
 * @param isScroll 是否外层有嵌套
 */
class WebFragment(var mUrl: String, var mTitle: String?,var mType: Int?,var isScroll:Boolean = false) :
        BasePFragment<FinancialMoreContract.Presenter<String>>(if (isScroll) R.layout.fragment_web_scroll else R.layout.fragment_web) {

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

    private val mWebClient = object : WebViewClient() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            if(Build.VERSION.SDK_INT<26) {
                view.loadUrl(url)
                return true
            }
            return false
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: com.tencent.smtt.export.external.interfaces.SslError?) {
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
            cacheUrl = view!!.url
            if (mListener != null) {
                if (mTitle.isNullOrEmpty()) {
                    mListener!!.changeTitle(title)
                } else {
                    mListener!!.changeTitle(mTitle)
                }
            }
        }

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
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
        baseWebView.settings = web_data.settings
        settings = baseWebView.setWeb(context)

    }

    override fun initData() {
        Log.i("WEB_URL:",mUrl)
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

    override fun onClick(v: View?) {
    }

    interface onChangeTitleListener {
        fun changeTitle(title: String?)
    }
}