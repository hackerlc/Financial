package com.boxin.financial.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.util.Log
import com.boxin.financial.view.gestureview.LockPatternUtils
import com.tencent.smtt.sdk.QbSdk
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig


/** app全局初始化，以及自定义属性位置
 * @author joker
 * Email:812405389@qq.com
 * @version 2017/11/6
 */
class App : Application() {
    companion object {
        lateinit var instance: App
            private set
            get

        @SuppressLint("StaticFieldLeak")
        lateinit var mLockPatternUtils: LockPatternUtils
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initX5()//webview x5
        initUM()//友盟
        mLockPatternUtils = LockPatternUtils(this)
    }

    private fun initUM() {
        UMConfigure.init(this,"5ac31694a40fa3306d00003b","WEB_H5", UMConfigure.DEVICE_TYPE_PHONE, "")
        UMConfigure.setLogEnabled(true)
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL)
        initShard()
    }

    private fun initShard(){
        PlatformConfig.setWeixin("wx7ee74b2815c2406e", "77970394f12ded1aa07ff3db59cb17c0")
        PlatformConfig.setQQZone("1106819104", "NOlDpI1C0RQYkOP4")
    }

    private fun initX5(){
        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
    }
}