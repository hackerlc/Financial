package com.boxin.financial.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.tencent.smtt.sdk.WebSettings;

import java.io.File;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
import static com.tencent.smtt.sdk.WebSettings.LOAD_NO_CACHE;
import static com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import static com.tencent.smtt.sdk.WebSettings.PluginState;

/**
 * BAndroid
 * Created by YichenZ on 2015/10/26 15:50.
 */
public class BaseWeb {
    protected static final String APP_CACAHE_DIRNAME = "/webcache";
    protected WebSettings settings;

    public WebSettings getSettings() {
        return settings;
    }

    public void setSettings(WebSettings settings) {
        this.settings = settings;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public WebSettings setWeb(Context context){
//        if(settings==null) {
//            new RuntimeException("settings is not null");
//        }
//        settings.setJavaScriptEnabled(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setAllowFileAccess(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setUseWideViewPort(true);
//        settings.setSupportMultipleWindows(true);
//        // webSetting.setLoadWithOverviewMode(true);
//        settings.setAppCacheEnabled(true);
//        // webSetting.setDatabaseEnabled(true);
//        settings.setDomStorageEnabled(true);
//        settings.setGeolocationEnabled(true);
//        settings.setAppCacheMaxSize(Long.MAX_VALUE);
//        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
//        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
//        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
//        // settings 的设计



        //开启JavaScript
        settings.setJavaScriptEnabled(true);
        //支持js打开窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        //默认加载格式utf-8
        settings.setDefaultTextEncodingName("utf-8");
        //启用页面缩放
        settings.setBuiltInZoomControls(false);
//        settings.setBlockNetworkImage(false);
        settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);

        //缓存模式
        settings.setCacheMode(LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);//开启DOM storage API
//        settings.setDatabaseEnabled(true);//启用数据库
        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);

        settings.setPluginState(PluginState.ON_DEMAND);
        settings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);

        String cacheDirPath = context.getDir("cache", Context.MODE_PRIVATE).getPath();
//        settings.setDatabasePath(cacheDirPath);//设置数据库缓存路径
//        settings.setAppCachePath(cacheDirPath);//设置应用换成路径
//        settings.setAllowFileAccess(true);
        // 开启应用缓存
        settings.setAppCacheEnabled(true);
        if(Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);//开启网页图片加载
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        return settings;
    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache(Context context){

        //清理Webview缓存数据库
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //WebView 缓存文件
        File appCacheDir = new File(context.getDir("cache", Context.MODE_PRIVATE).getPath());
        Log.e("appCacheDir", "appCacheDir path=" + context.getDir("files", Context.MODE_PRIVATE).getPath());

        File webviewCacheDir = new File(context.getDir("cache", Context.MODE_PRIVATE).getPath());
        Log.e("webviewCacheDir", "webviewCacheDir path=" + context.getDir("cache", Context.MODE_PRIVATE).getPath());

        File app_webview = new File(context.getDir("app_webview", Context.MODE_PRIVATE).getPath());
        Log.e("app_webview", "app_webview path=" + context.getDir("app_webview", Context.MODE_PRIVATE).getPath());

//        //删除webview 缓存目录
//        if(webviewCacheDir.exists()){
//            deleteFile(webviewCacheDir);
//        }
//        //删除webview 缓存 缓存目录
//        if(appCacheDir.exists()){
//            deleteFile(appCacheDir);
//        }
        //app_webview 缓存 缓存目录
//        if(app_webview.exists()){
//            deleteFile(app_webview);
//        }
    }


    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

//        Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
//            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }
}
