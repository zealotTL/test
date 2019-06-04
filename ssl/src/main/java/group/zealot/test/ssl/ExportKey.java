package group.zealot.test.ssl;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.*;
import java.security.cert.Certificate;

public class ExportKey {

    //导出证书 base64格式
    public static void exportCert(KeyStore keystore, String alias, String exportFile) throws Exception {
        Certificate cert = keystore.getCertificate(alias);
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(cert.getEncoded());
        FileWriter fw = new FileWriter(exportFile);
        fw.write("-----BEGIN CERTIFICATE-----\r\n");    //非必须
        fw.write(encoded);
        fw.write("\r\n-----END CERTIFICATE-----");  //非必须
        fw.close();
    }

    //得到KeyPair
    public static KeyPair getKeyPair(KeyStore keystore, String alias, char[] password) {
        try {
            Key key = keystore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                Certificate cert = keystore.getCertificate(alias);
                PublicKey publicKey = cert.getPublicKey();
                return new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }

    //导出私钥
    public static void exportPrivateKey(PrivateKey privateKey, String exportFile) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(privateKey.getEncoded());
        FileWriter fw = new FileWriter(exportFile);
        fw.write("-----BEGIN RSA PRIVATE KEY-----\r\n");  //非必须
        fw.write(encoded);
        fw.write("\r\n-----END RSA PRIVATE KEY-----");        //非必须
        fw.close();
    }

    //导出公钥
    public static void exportPublicKey(PublicKey publicKey, String exportFile) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(publicKey.getEncoded());
        FileWriter fw = new FileWriter(exportFile);
        fw.write("-----BEGIN RSA PUBLIC KEY-----\r\n");       //非必须
        fw.write(encoded);
        fw.write("\r\n-----END RSA PUBLIC KEY-----");     //非必须
        fw.close();
    }

    public static void main(String args[]) throws Exception {
        String dir = "C:\\Users\\zealot\\Desktop\\SSL\\";
        String keyStoreType = "JKS";
        String keystoreFile = dir + "tomcat.keystore";
        String password = "password";
        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        keystore.load(new FileInputStream(new File(keystoreFile)), password.toCharArray());
        String alias = "tomcat";
        String exportCertFile = dir + "tomcat.crt";
        String exportPrivateFile = dir + "tomcat.private.key";
        String exportPublicFile = dir + "tomcat.public.key";
        ExportKey.exportCert(keystore, alias, exportCertFile);
        KeyPair keyPair = ExportKey.getKeyPair(keystore, alias, password.toCharArray());
        ExportKey.exportPrivateKey(keyPair.getPrivate(), exportPrivateFile);
        ExportKey.exportPublicKey(keyPair.getPublic(), exportPublicFile);
        System.out.println("OK");
    }
}