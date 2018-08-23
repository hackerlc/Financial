package gear.yc.com.gearlibrary.utils.web;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.WebSettings;

import java.io.File;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebSettings setWeb(Context context){
        if(settings==null) {
            new RuntimeException("settings is not null");
        }
        settings.setJavaScriptEnabled(true);//开启JavaScript
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//缓存模式
        settings.setDefaultTextEncodingName("utf-8");//默认加载格式utf-8
        settings.setDomStorageEnabled(true);//开启DOM storage API
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持js打开窗口
//        settings.setDatabaseEnabled(true);//启用数据库
        settings.setUseWideViewPort(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(false);//启用页面缩放
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        String cacheDirPath = context.getDir("cache", Context.MODE_PRIVATE).getPath();
//        settings.setDatabasePath(cacheDirPath);//设置数据库缓存路径
//        settings.setAppCachePath(cacheDirPath);//设置应用换成路径
//        settings.setAllowFileAccess(true);
//        settings.setAppCacheEnabled(true);//开启应用缓存
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
