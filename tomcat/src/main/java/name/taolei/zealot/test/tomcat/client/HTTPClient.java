package name.taolei.zealot.test.tomcat.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

@Service("httpClient")
public class HTTPClient {
    
    public void doGet(String host, int port, String uri) {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder("GET " + uri + " HTTP/1.1\r\n");
        // HTTP请求头
        sb.append("Accept: */*\r\n");
        sb.append("Accept-Language: zh-cn\r\n");
        sb.append("Accept-Encoding: gzip, deflate\r\n");
        sb.append("User-Agent: HTTPClient\r\n");
        sb.append("Host: localhost:8080\r\n");
        sb.append("Connection: Keep-Alive\r\n");
        
        OutputStream socketOut;
        try {
            socketOut = socket.getOutputStream();
            socketOut.write(sb.toString().getBytes());
            Thread.sleep(2000);
            InputStream socketIn = socket.getInputStream();
            int size = socketIn.available();
            byte buffer[] = new byte[size];
            socketIn.read(buffer);
            System.out.println(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
        }
        
    }
}
