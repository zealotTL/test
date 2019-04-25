package group.zealot.test.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HTTPClient {

    Logger logger = LoggerFactory.getLogger(getClass());

    public void doGet(String host, int port, String uri) {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
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
            byte[] buffer = new byte[size];
            socketIn.read(buffer);
            logger.info("返回: \n" + new String(buffer));
        } catch (IOException | InterruptedException e) {
            logger.error("socket 连接读取 异常", e);
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.error("socket.close() 异常", e);
                }
            }
        }
    }
}
