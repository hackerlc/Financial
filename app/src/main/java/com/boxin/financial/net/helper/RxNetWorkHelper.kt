package com.boxin.financial.net.helper

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.boxin.financial.base.App
import io.reactivex.FlowableTransformer

/**
 * 类说明
 * @author joker
 * Email:812405389@qq.com
 * @version 2017/12/20
 */
class RxNetWorkHelper {
    companion object {
        fun <T> isNetWork(): FlowableTransformer<T, T> {
            return FlowableTransformer {
                it.doOnSubscribe{
                    if (!isNetworkConnected(App.instance)) {
                        throw ThrowableNetWorkError("没有连接，请检查网络设置")
                    }
                }
            }
        }

        /**
         * 判断是否有网络连接
         * @param context
         * @return
         */
        @SuppressLint("MissingPermission")
        private fun isNetworkConnected(context: Context): Boolean {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
            return false
        }

    }
}