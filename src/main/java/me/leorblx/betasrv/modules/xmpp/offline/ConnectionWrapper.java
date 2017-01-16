package me.leorblx.betasrv.modules.xmpp.offline;

import javax.net.ssl.*;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyStore;

public class ConnectionWrapper
{    /*
     * genkey command keytool -genkey -keyalg RSA -alias selfsigned -keystore
     * keystore.jks \ -storepass 123456 -validity 360 -keysize 2048
     * 
     * need to run with vm param -Djsse.enableCBCProtection=false
     */
    public static void wrapXmppTalk(XmppClient xmppClient)
    {
        try {
            ClassLoader classLoader = ConnectionWrapper.class.getClassLoader();
            InputStream keyInputStream = classLoader.getResourceAsStream("keystore.jks");
            InputStream keyStoreInputStream = classLoader.getResourceAsStream("keystore.jks");
            Socket socket = xmppClient.getSocket();
            KeyStore ksKeys = KeyStore.getInstance("JKS");
            ksKeys.load(keyInputStream, "123456".toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ksKeys, "123456".toCharArray());
            InputStream keyStoreIS = keyStoreInputStream;
            char[] keyStorePassphrase = "123456".toCharArray();
            ksKeys.load(keyStoreIS, keyStorePassphrase);
            kmf.init(ksKeys, keyStorePassphrase);
            KeyStore ksTrust = KeyStore.getInstance("JKS");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ksTrust);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
            SSLSocketFactory sf = sslContext.getSocketFactory();
            SSLSocket s = (SSLSocket) (sf.createSocket(socket, remoteAddress.getHostName(), socket.getPort(), true));
            s.setUseClientMode(false);
            xmppClient.setSocket(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
