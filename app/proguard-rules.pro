# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

#############################################
#
# 对于一些基本指令的添加
#
#############################################
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*

#############################################
# Use kotlin
#############################################
-dontwarn kotlin.**
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
#一般以下情况都会不混淆：
#1.使用了自定义控件那么要保证它们不参与混淆
#2.使用了枚举要保证枚举不被混淆
#3.对第三方库中的类不进行混淆
#4.运用了反射的类也不进行混淆
#5.使用了 Gson 之类的工具要使 JavaBean 类即实体类不被混淆
#6.在引用第三方库的时候，一般会标明库的混淆规则的，建议在使用的时候就把混淆规则添加上去，免得到最后才去找
#7.有用到 WebView 的 JS 调用也需要保证写的接口方法不混淆，原因和第一条一样
#8.Parcelable 的子类和 Creator 静态成员变量不混淆，否则会产生 Android.os.BadParcelableException 异常
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

# gear library
-keep class gear.yc.com.gearlibrary.** {*;}

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}
# glide 的混淆代码
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }
# ImmersionBar
 -keep class com.gyf.barlibrary.* {*;}
# okhttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
# Gson
-keep class com.google.code.gson.** {*;}
-keep class com.boxin.financial.entity.** {*;}
# Retrofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
# Retain service method parameters.
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}
# XTabLayout
#-keep class com.androidkun:XTabLayout.** {*;}
###############################
## 友盟统计
###############################
-keep class com.umeng.** {*;}
#-keepclassmembers class * {
#   public <init> (org.json.JSONObject);
#}
###############################
## 友盟分享
###############################
#-dontshrink
#-dontoptimize
#-dontwarn com.google.android.maps.**
#-dontwarn android.webkit.WebView
#-dontwarn com.umeng.**
#-dontwarn com.tencent.weibo.sdk.**
#-dontwarn com.facebook.**
#-keep public class javax.**
#-keep public class android.webkit.**
#-dontwarn android.support.v4.**
#-keep enum com.facebook.**
#-keepattributes Exceptions,InnerClasses,Signature
#-keepattributes *Annotation*
#-keepattributes SourceFile,LineNumberTable
#-keep public interface com.facebook.**
#-keep public interface com.tencent.**
#-keep public interface com.umeng.socialize.**
#-keep public interface com.umeng.socialize.sensor.**
#-keep public interface com.umeng.scrshot.**
#-keep public class com.umeng.socialize.* {*;}
#-keep class com.facebook.**
#-keep class com.facebook.** { *; }
#-keep class com.umeng.scrshot.**
#-keep public class com.tencent.** {*;}
#-keep class com.umeng.socialize.sensor.**
#-keep class com.umeng.socialize.handler.**
#-keep class com.umeng.socialize.handler.*
#-keep class com.umeng.weixin.handler.**
#-keep class com.umeng.weixin.handler.*
#-keep class com.umeng.qq.handler.**
#-keep class com.umeng.qq.handler.*
#-keep class UMMoreHandler{*;}
#-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
#-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
#-keep class im.yixin.sdk.api.YXMessage {*;}
#-keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
#-keep class com.tencent.mm.opensdk.** {
#   *;
#}
#-keep class com.tencent.wxop.** {
#   *;
#}
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
#-dontwarn twitter4j.**
#-keep class twitter4j.** { *; }
#-keep class com.tencent.** {*;}
#-dontwarn com.tencent.**
#-keep class com.kakao.** {*;}
#-dontwarn com.kakao.**
#-keep public class com.umeng.com.umeng.soexample.R$*{
#    public static final int *;
#}
#-keep public class com.linkedin.android.mobilesdk.R$*{
#    public static final int *;
#}
#-keep class com.tencent.open.TDialog$*
#-keep class com.tencent.open.TDialog$* {*;}
#-keep class com.tencent.open.PKDialog
#-keep class com.tencent.open.PKDialog {*;}
#-keep class com.tencent.open.PKDialog$*
#-keep class com.tencent.open.PKDialog$* {*;}
#-keep class com.umeng.socialize.impl.ImageImpl {*;}
#-keep class com.sina.** {*;}
#-dontwarn com.sina.**
#-keep class  com.alipay.share.sdk.** {
#   *;
#}
#-keepnames class * implements android.os.Parcelable {
#    public static final ** CREATOR;
#}
#-keep class com.linkedin.** { *; }
#-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
#-keepattributes Signature
# hide the original source file name.
#-renamesourcefileattribute SourceFile
