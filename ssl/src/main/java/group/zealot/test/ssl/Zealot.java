package group.zealot.test.ssl;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

public class Zealot {
    public static KeyStore getKeyStore(String password, String keyStorePath)
            throws Exception {
        // 实例化密钥库
        KeyStore ks = KeyStore.getInstance("PKCS12");
        // 获得密钥库文件流
        FileInputStream is = new FileInputStream(keyStorePath);
        // 加载密钥库
        ks.load(is, password.toCharArray());
        // 关闭密钥库文件流
        is.close();
        return ks;
    }

    public static KeyStore getTrustStore(String password, String keyStorePath)
            throws Exception {
        // 实例化密钥库
        KeyStore ks = KeyStore.getInstance("JKS");
        // 获得密钥库文件流
        FileInputStream is = new FileInputStream(keyStorePath);
        // 加载密钥库
        ks.load(is, password.toCharArray());
        // 关闭密钥库文件流
        is.close();
        return ks;
    }

    public static SSLContext getSSLContext(String password,
                                           String keyStorePath, String trustStorePath) throws Exception {
        // 实例化密钥库
        KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance(KeyManagerFactory.getDefaultAlgorithm());
        // 获得密钥库
        KeyStore keyStore = getKeyStore(password, keyStorePath);
        // 初始化密钥工厂
        keyManagerFactory.init(keyStore, password.toCharArray());

        // 实例化信任库
        TrustManagerFactory trustManagerFactory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // 获得信任库
        KeyStore trustStore = getTrustStore(password, trustStorePath);
        // 初始化信任库
        trustManagerFactory.init(trustStore);
        // 实例化SSL上下文
        SSLContext ctx = SSLContext.getInstance("TLS");
        // 初始化SSL上下文
        ctx.init(keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(), null);
        // 获得SSLSocketFactory
        return ctx;
    }

    /**
     * 初始化HttpsURLConnection.
     *
     * @param password       密码
     * @param keyStorePath   密钥库路径
     * @param trustStorePath 信任库路径
     * @throws Exception
     */
    public static void initHttpsURLConnection(String password, String keyStorePath, String trustStorePath) throws Exception {
        SSLContext sslContext = null;

        try {
            sslContext = getSSLContext(password, keyStorePath, trustStorePath);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        if (sslContext != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
                    .getSocketFactory());
        }
        //验证证书域名不一致的以下请求host可以正常请求服务器获得返回
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession paramSSLSession) {
                if ("证书IP".equals(hostname)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    /**
     * 发送请求.
     *
     * @param httpsUrl 请求的地址
     * @param xmlStr   请求的数据
     */

    public static void post(String httpsUrl, String xmlStr) {
        HttpsURLConnection urlCon = null;
        try {
            urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-Length",
                    String.valueOf(xmlStr.getBytes().length));
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("Content-Type", "application/json");

            urlCon.connect();
            OutputStreamWriter writer = new OutputStreamWriter(urlCon.getOutputStream());
            //发送参数
            writer.write(xmlStr);
            //清理当前编辑器的左右缓冲区，并使缓冲区数据写入基础流
            writer.flush();
            writer.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line;
            System.out.println("返回body");
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            urlCon.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String dir = "C:\\Users\\zealot\\Desktop\\SSL\\";
        // 密码
        String password = "password";
        // 密钥库
        String keyStorePath = dir + "client.key.p12";
        // 信任库
        String trustStorePath = dir + "test.keystore";//tomcat.keystore  tomcat.cer
        // 本地起的https服务
        String httpsUrl = "https://localhost:8080/api";
        // 传输文本
        Zealot.initHttpsURLConnection(password, keyStorePath, trustStorePath);
        String body = "主体消息";
        // 发起请求
        Zealot.post(httpsUrl, body);
        System.exit(0);
    }

}