package group.zealot.test.socket.listen;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HTTPService {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 开启某个端口的监听
     */
    public void startHTTPService(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            logger.info("监听端口" + port);
        } catch (IOException e) {
            logger.error("socket 监听 异常", e);
            return;
        }
        Socket socket = null;
        while (serverSocket != null) {
            try {
                socket = serverSocket.accept();
                logger.info("建立TCP连接，请求端地址：" + socket.getInetAddress() + ":" + socket.getPort());
                service(socket);
            } catch (InterruptedException | IOException e) {
                logger.error("socket 连接写入 异常", e);
            }
        }
    }

    private void service(Socket socket) throws IOException, InterruptedException {
        InputStream socketIn = socket.getInputStream();
        Thread.sleep(500);
        int size = socketIn.available();
        byte[] buffer = new byte[size];
        socketIn.read(buffer);
        String request = new String(buffer);
        logger.info("请求信息: \n" + request);

        String firstLineOfRequest = request.substring(0, request.indexOf("\r\n"));

        String[] parts = firstLineOfRequest.split(" ");
        String uri = parts[1];

        String contentType;
        if (uri.contains("html") || uri.contains("htm")) {
            contentType = "text/html";
        } else if (uri.contains("jpg") || uri.contains("jpeg")) {
            contentType = "image/jpeg";
        } else if (uri.contains("gif")) {
            contentType = "image/gif";
        } else {
            contentType = "application/octet-stream";
        }

        String responseFirstLine = "HTTP/1.1 200 OK\r\n";

        String responseHeader = "Content-Type:" + contentType + "\r\n\r\n";

        OutputStream socketOut = socket.getOutputStream();

        socketOut.write(responseFirstLine.getBytes());
        socketOut.write(responseHeader.getBytes());

        InputStream in = HTTPService.class.getResourceAsStream("/" + uri);
        int len = 0;
        buffer = new byte[128];
        while ((len = in.read(buffer)) != -1) {
            socketOut.write(buffer, 0, len);
        }
        Thread.sleep(1000);
        socket.close();
    }
}
