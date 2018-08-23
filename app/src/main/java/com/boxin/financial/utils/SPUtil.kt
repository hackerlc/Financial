package com.boxin.financial.utils

import android.content.Context
import android.util.Log
import com.boxin.financial.base.App
import gear.yc.com.gearlibrary.utils.encryption.BASE64
import java.io.*
import android.content.SharedPreferences
import com.boxin.financial.R.id.phone
import com.boxin.financial.entity.User
import com.boxin.financial.view.gestureview.LockPatternView
import com.umeng.socialize.utils.DeviceConfig.context


/**
 * sp 缓存工具类
 * @author joker
 * Email:812405389@qq.com
 * @version 2018/3/26
 */
class SPUtil {
    companion object {
        /**
         * 缓存文件名
         */
        val PREFERENCE_FILE_NAME = "boxin_financial"
        /**
         * 缓存登录信息
         */
        val USER_PSD = "user_psd"
        /**
         * 首次登录
         */
        val IS_SPLASH = "is_splash"
        /**
         * 是否登录
         */
        val IS_LOGIN = "is_login"

        fun setLogin(isLogin: Boolean){
            val prefe = App.Companion.instance.getSharedPreferences(IS_LOGIN,0)
            val editor = prefe.edit()
            editor.putBoolean("isLogin", isLogin)
            editor.apply()
        }

        fun isLogin(): Boolean{
            val prefe = App.Companion.instance.getSharedPreferences(IS_LOGIN, 0)
            return prefe.getBoolean("isLogin",false)
        }

        fun setFirst(context: Context){
            val prefe = context.getSharedPreferences(IS_SPLASH,0)
            val editor = prefe.edit()
            editor.putBoolean("isSplash", false)
            editor.apply()
        }

        fun isFirst(context: Context): Boolean{
            val prefe = context.getSharedPreferences(IS_SPLASH, 0)
            return prefe.getBoolean("isSplash",true)
        }

        fun setLockNum(num: Int,phone: String?){
            var mPhone = phone
            if (mPhone.isNullOrEmpty()){
                mPhone = "lock_phone"
            }
            val prefe = App.Companion.instance.getSharedPreferences(mPhone,0)
            val editor = prefe.edit()
            editor.putInt("lock_num", num)
            editor.apply()
        }

        fun getLockNum(phone: String?): Int{
            var mPhone = phone
            if (mPhone.isNullOrEmpty()){
                mPhone = "lock_phone"
            }
            val prefe = App.Companion.instance.getSharedPreferences(mPhone, 0)
            return prefe.getInt("lock_num",0)
        }

        fun setShoushiLock(pattern: List<LockPatternView.Cell>,
                           key: String) {
            saveObj(pattern, key)
        }

        fun getShoushiLock(key: String): List<LockPatternView.Cell> {
            val any = readObj(key)
            if (any != null){
                any as List<LockPatternView.Cell>
                return  any
            }
            return emptyList()
        }

        /**
         * 储存一个对象
         */
        fun <T> saveObj(obj: T, key: String){
            val _obj = obj
            val prefe = App.Companion.instance.getSharedPreferences(PREFERENCE_FILE_NAME,0)
            val baos = ByteArrayOutputStream()
            try {
                val oos = ObjectOutputStream(baos)
                oos.writeObject(_obj)
                val listBase64 = BASE64.encode(baos.toByteArray())
                val editor = prefe.edit()
                editor.putString(key, listBase64)
                editor.apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun readObj(key: String): Any?{
            var obj: Any? = null
            val prefe = App.Companion.instance.getSharedPreferences(PREFERENCE_FILE_NAME,0)
            val replysBase64 = prefe.getString(key, "")
            if (replysBase64.isEmpty()){
                return obj
            }
            val base64 = BASE64.decode(replysBase64)
            val bais = ByteArrayInputStream(base64)
            try {
                val ois = ObjectInputStream(bais)
                try {
                    obj = ois.readObject()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
            } catch (e: StreamCorruptedException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return obj
        }
    }
}