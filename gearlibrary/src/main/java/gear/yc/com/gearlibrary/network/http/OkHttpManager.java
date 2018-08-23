package gear.yc.com.gearlibrary.network.http;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import gear.yc.com.gearlibrary.network.cookie.CookieManger;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * GearApplication
 * okHttp3 管理类，封装了okHttp的网络连接方法
 * OkHttp3 开启Stetho听诊器
 * Created by YichenZ on 2016/3/8 13:41.
 *
 * @version 1.0
 * 创建
 */
public class OkHttpManager {
    protected static OkHttpManager instance;

    public static OkHttpManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpManager.class) {
                if (instance == null) {
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    protected static OkHttpClient okHttpClient;

    protected int mTimeOut = 15;
    protected String headerKey, headerValue;
    protected boolean isLog = true;

    public OkHttpManager() {
    }

    /**
     * 设置header
     * 暂时不支持多个header
     *
     * @param headerKey
     * @param headerValue
     * @return
     */
    public OkHttpManager setHeader(String headerKey, String headerValue) {
        this.headerKey = headerKey;
        this.headerValue = headerValue;
        return instance;
    }

    /**
     * 设置超时时间
     *
     * @param timeOut 数值
     * @return
     */
    public OkHttpManager setTimeOut(int timeOut) {
        mTimeOut = timeOut;
        return instance;
    }

    /**
     * 是否显示Log 默认为true
     *
     * @param isLog true 显示
     * @return
     */
    public OkHttpManager setLog(boolean isLog) {
        this.isLog = isLog;
        return instance;
    }

    public OkHttpManager build(Context context) {
        if (okHttpClient == null) {
            try {
                setCertificates(context.getAssets().open("zhecaibao.cer"));
            } catch (Exception e) {
                sslSocketFactory = null;
                trustManager = null;
            }
            OkHttpClient.Builder obr = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(isLog ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                    .cookieJar(new CookieManger(context))
                    .retryOnConnectionFailure(true)
                    .connectTimeout(mTimeOut, TimeUnit.SECONDS);
            if (sslSocketFactory!=null && trustManager != null) {
                obr.sslSocketFactory(sslSocketFactory, trustManager);
            }
            okHttpClient = obr.build();
        }
        return instance;
    }

    /**
     * @return OkHttp3 Client
     */
    public OkHttpClient getClient() {
        return okHttpClient;
    }

    /**
     * 通过okhttpClient来设置证书
     *
     * @param clientBuilder OKhttpClient.builder
     * @param certificates 读取证书的InputStream
     */
    X509TrustManager trustManager;
    SSLSocketFactory sslSocketFactory;

    private void setCertificates(InputStream... certificates) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        int index = 0;
        for (InputStream certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificateFactory
                    .generateCertificate(certificate));
            if (certificate != null) {
                certificate.close();
            }
        }
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        trustManager = (X509TrustManager) trustManagers[0];
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        sslSocketFactory = sslContext.getSocketFactory();
    }

}
