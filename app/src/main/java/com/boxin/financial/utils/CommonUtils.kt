package com.boxin.financial.utils

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.Bitmap
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager
import android.text.InputFilter
import android.text.Selection
import android.text.Spannable
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText

import com.boxin.financial.base.App
import com.boxin.financial.config.APIConfig
import com.boxin.financial.config.AppConfig
import com.boxin.financial.view.dialog.LoadingDialog

import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Administrator on 2016/11/22.
 */

object CommonUtils {


    /**
     * @param @param  context
     * @param @return
     * @return String
     * @throws
     * @Title: getVersionName
     * @Description: 获取当前应用的版本号
     */
    // 获取packagemanager的实例
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    val versionName: String
        get() {
            try {
                val packageManager = App.instance.packageManager
                val packInfo: PackageInfo
                packInfo = packageManager.getPackageInfo(App.instance.packageName,
                        0)
                val version = packInfo.versionName
                return version
            } catch (e: NameNotFoundException) {
                e.printStackTrace()
                return ""
            }

        }


    val versionCode: Int
        get() {
            var packInfo: PackageInfo? = null
            try {
                val packageManager = App.instance.packageManager
                packInfo = packageManager.getPackageInfo(App.instance.packageName, 0)
            } catch (e: NameNotFoundException) {
                e.printStackTrace()
            }

            return if (packInfo != null) packInfo.versionCode else 0
        }

    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     *
     * @param str 无逗号的数字
     * @return 加上逗号的数字
     */
    fun addComma(str: String?): String {
        if ("" == str || str == null) {
            return ""
        }
        //判断字符串是否有.
        val numStr: String
        var dian = ""
        if (str.contains(".")) {
            //截取字符串
            numStr = str.substring(0, str.indexOf("."))
            dian = str.substring(str.indexOf("."), str.length)
        } else {
            numStr = str
        }
        // 将传进数字反转
        val reverseStr = StringBuilder(numStr).reverse().toString()
        var strTemp = ""
        for (i in 0 until reverseStr.length) {
            if (i * 3 + 3 > reverseStr.length) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length)
                break
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ","
        }
        // 将[789,456,] 中最后一个[,]去除
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length - 1)
        }
        // 将数字重新反转
        val resultStr = StringBuilder(strTemp).reverse().toString()
        return resultStr + dian
    }

    /**
     * 四舍五入保留两位小数
     * @return
     */
    fun onFormatTwo(str: Double): String {
        if (str <= 0.0) {
            return "0.00"
        }
        val num = java.lang.Double.valueOf(str)
        return DecimalFormat("0.00").format(num)
    }

    fun onFormatTwo(str: String?): String {
        if ("" == str || str == null) {
            return "0"
        }
        val num = java.lang.Double.valueOf(str)!!
        return DecimalFormat("0.00").format(num)
    }

    /**
     * 四舍五入
     * @return
     */
    fun onFormat(str: String?): String {
        if ("" == str || str == null) {
            return "0.0"
        }
        val num = java.lang.Double.valueOf(str)!!
        return DecimalFormat("0.0").format(num)
    }


    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * A - Z 0 - 9
     */
    val mCode = charArrayOf('a', 'b', 'd', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r','s', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    /**
     * 图形验证码
     */
    fun fetchCode(): String
    {
        return "${mCode[Random().nextInt(36)]}${mCode[Random().nextInt(36)]}${mCode[Random().nextInt(36)]}${mCode[Random().nextInt(36)]}"
    }
    /**
     * 判断字符串是否是数字
     * @param str
     * @return
     */
    fun isNumeric(str: String): Boolean {
        val pattern = Pattern.compile("(^[1-9]([0-9]+)?(\\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\\.[0-9]([0-9])?$)")
        val isNum = pattern.matcher(str)
        return isNum.matches()
    }

    /**
     * 返回银行卡4位
     */
    fun fetchBankCard4Char(bankCard: String?): String{
        if (bankCard.isNullOrEmpty()){
            return ""
        }

        if (bankCard!!.length < 4) {
            return  bankCard
        }

        return bankCard.substring(bankCard.length - 4,bankCard.length)
    }

    // 安装下载后的apk文件
    fun instanll(file: File, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

    fun getScreenMaxWidth(context: Context, paramInt: Int): Int {
        var localObject: Any = DisplayMetrics()
        try {
            val localDisplayMetrics = context.applicationContext.resources.displayMetrics
            localObject = localDisplayMetrics
            return localObject.widthPixels - dip2px(context, paramInt.toFloat())
        } catch (localException: Exception) {
            while (true) {
                localException.printStackTrace()
                (localObject as DisplayMetrics).widthPixels = 640
            }
        }

    }


    fun getScreenMaxHeight(paramContext: Context, paramInt: Int): Int {
        var localObject: Any = DisplayMetrics()
        try {
            val localDisplayMetrics = paramContext.applicationContext.resources.displayMetrics
            localObject = localDisplayMetrics
            return localObject.heightPixels - dip2px(paramContext, paramInt.toFloat())
        } catch (localException: Exception) {
            while (true) {
                localException.printStackTrace()
                (localObject as DisplayMetrics).heightPixels = 960
            }
        }

    }


    /**
     * 讲电话号码中间变成*
     *
     * @param str
     * @param type 类型   0手机(默认)  1身份证号 2银行卡号
     * @return
     */
    fun changeStrTrip(str: String?,type: Int = 0): String? {
        if (str.isNullOrEmpty()){
            return ""
        }
        val sb = StringBuilder()
        when(type){
            0 -> {
                if (str!!.length > 8) {
                    for (i in 0 until str.length) {
                        val c = str[i]
                        if (i >= 3 && i <= 8) {
                            sb.append('*')
                        } else {
                            sb.append(c)
                        }
                    }
                }
            }
            1 -> {
                if (str!!.length > 13) {
                    for (i in 0 until str.length) {
                        val c = str[i]
                        if (i >= 2 && i <= 13) {
                            sb.append('*')
                        } else {
                            sb.append(c)
                        }
                    }
                }
            }
            2 -> {
                if (str!!.length > 12) {
                    for (i in 0 until str.length) {
                        val c = str[i]
                        if (i >= 0 && i <= 12) {
                            sb.append('*')
                        } else {
                            sb.append(c)
                        }
                    }
                }
            }
        }
        return sb.toString()

        return str
    }

    fun matchPhone(text: String?): Boolean {
        return if (text.isNullOrEmpty()) {false} else {
            return Pattern.compile("^1(3|4|5|6|7|8|9)\\d{9}$").matcher(text).matches()
        }
    }

    /**
     * 密码判断
     */
    fun matchPassword(text: String?): Boolean {
        return if (text.isNullOrEmpty()) {false} else {
            return Pattern.compile("^[0-9A-Za-z`~!@#\$%^&*/()_+-=\\[\\]{}|;':\",.<>/?]*$").matcher(text).matches()
        }
    }


    fun getNetworkOperatorName(context: Context): String {
        val telephonyManager = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.networkOperatorName

    }

    //
    //    /**
    //     * HTTP请求签名
    //     *
    //     * @param parames
    //     * @param reqTime
    //     * @return
    //     */
    //    public static String generateSign(FwsHttpParames parames, String reqTime) {
    //        StringBuilder builder = new StringBuilder(Constant.secretKey);
    //        builder.append(reqTime);
    //        if (parames != null && !parames.isEmpty()) {
    //            for (Map.Entry<String, ?> entry : parames.entrySet()) {
    //                Object value = entry.getValue();
    //                String property = entry.getKey();
    //                if (value != null) {
    //                    builder.append(property + value);
    //                }
    //            }
    //        }
    //
    //        System.out.println("************HTTP请求签名成功的数据*****************" + builder.toString());
    //        String s = new String(Hex.encodeHex(DigestUtils.md5(new String(Hex.encodeHex(DigestUtils.md5(builder.toString()))))));
    ////        String s1 = MD5Utils.getMd5Value(MD5Utils.getMd5Value(builder.toString()));
    //        return s;
    //    }


    fun getPixColor(src: Bitmap): Int {
        val pixelColor: Int
        pixelColor = src.getPixel(5, 5)
        return pixelColor
    }

    /**
     * 禁止EditText输入空格
     * @param editText view
     * @param length 长度控制
     */
    fun setEditTextInhibitInputSpace(editText: EditText, length: Int) {
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            if (" " == source || "\n" == source || dend >= length) {
                ""
            } else {
                null
            }
        }
        editText.filters = arrayOf(filter)
    }

    /**
     * 获取bitmap图片的大小
     *
     * @param bitmap
     * @return
     */
    fun getBitmapsize(bitmap: Bitmap): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            bitmap.byteCount.toLong()
        } else (bitmap.rowBytes * bitmap.height).toLong()
        // Pre HC-MR1

    }

    /**
     * 获取bitmap图片的大小后转换成kb
     *
     * @param bitmap
     * @return
     */
    fun convertFileSize(bitmap: Bitmap): String {
        val kb: Long = 1024
        val f = getBitmapsize(bitmap).toFloat() / kb
        return String.format(if (f > 100) "%.0f" else "%.1f", f)
    }


    /**
     * 安卓获取状态栏(Status Bar)高度
     *
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val height = resources.getDimensionPixelSize(resourceId)
        Log.v("dbw", "Status height:$height")
        return height
    }


    /**
     * 判断是否有虚拟按键
     *
     * @param context
     * @return
     */
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
        }

        return hasNavigationBar
    }

    fun hideBottomUIMenu(context: Activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = context.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = context.window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }


    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    fun getBottomStatusHeight(context: Context): Int {
        val totalHeight = getDpi(context)

        val contentHeight = getScreenHeight(context)

        return totalHeight - contentHeight
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }
    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    fun getDpi(context: Context): Int {
        var dpi = 0
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        val c: Class<*>
        try {
            c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, displayMetrics)
            dpi = displayMetrics.heightPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dpi
    }


    /**
     * 将Edittext光标定位到最后一位
     * @param editText
     */
    fun setEditTextCursorLocation(editText: EditText) {
        val text = editText.text
        if (text is Spannable) {
            val spanText = text as Spannable
            Selection.setSelection(spanText, text.length)
        }
    }


    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    fun isOPen(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return if (gps || network) {
            true
        } else false

    }


    /**
     * 强制帮用户打开GPS
     * @param context
     */
    fun openGPS(context: Context) {
        val GPSIntent = Intent()
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider")
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE")
        GPSIntent.data = Uri.parse("custom:3")
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send()
        } catch (e: PendingIntent.CanceledException) {
            e.printStackTrace()
        }

    }

    /**
     * 通过包名判断有没有安装某个应用
     * @param context
     * @param packageName
     * @return
     */
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        val packageManager = context.packageManager
        val pinfo = packageManager.getInstalledPackages(0)
        val pName = ArrayList<String>()
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                pName.add(pn)
            }
        }
        return pName.contains(packageName)
    }


    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.activeNetworkInfo
            if (info != null && info.isConnected) {
                // 当前网络是连接的
                if (info.state == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true
                }
            }
        }
        return false
    }


    /**
     * 将.0改为整数
     * @param sMoney
     * @return
     */
    fun formatMoney(sMoney: String): String {
        val formatString: String

        if (!TextUtils.isEmpty(sMoney)) {
            val money = java.lang.Float.valueOf(sMoney)!!
            if (money == 0f) {
                formatString = "0"
            } else {
                if (money % money.toInt() == 0f) {
                    formatString = money.toInt().toString() + ""
                } else {
                    val decimalFormat = DecimalFormat(".00")//构造方法的字符格式这里如果小数不足2位,会以0补足.
                    formatString = decimalFormat.format(money.toDouble())//format 返回的是字符串
                }
            }

        } else {
            formatString = "0"
        }

        return formatString

    }
    fun encode(text: String): String {
        try {
            //获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            //对字符串加密，返回字节数组
            val digest:ByteArray = instance.digest(text.toByteArray())
            var sb : StringBuffer = StringBuffer()
            for (b in digest) {
                //获取低八位有效值
                var i :Int = b.toInt() and 0xff
                //将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    //如果是一位的话，补0
                    hexString = "0" + hexString
                }
                sb.append(hexString)
            }
            return sb.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 用户密码登录3次判断
     */
    fun onPhonePasswordCheck(phone: String?): Boolean{
        if (phone.isNullOrEmpty()){
            return false
        }
        var num = AppConfig.psdPhoneMap[phone]
        if (num == null){
            num = 0
            AppConfig.psdPhoneMap[phone!!] = num
        }
        return num > 3
    }

    fun onPhonePasswordAdd(phone: String){
        var num = AppConfig.psdPhoneMap[phone]
        if (num == null){
            num = 0
        } else {
            num ++
        }
        AppConfig.psdPhoneMap[phone] = num
    }

    /**
     * 显示LoadingDialog
     *
     * @param loadingDialog
     */
    fun showLoading(loadingDialog: LoadingDialog?) {
        if (loadingDialog != null && !loadingDialog.isShowing) {
            loadingDialog.showDialog()
        }
    }

    /**
     * 隐藏LoadingDialog
     *
     * @param loadingDialog
     */
    fun hideLoading(loadingDialog: LoadingDialog?) {
        if (loadingDialog != null && loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }
}
