package name.taolei.zealot.test.tomcat.listen;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.springframework.stereotype.Service;

@Service("httpService")
public class HTTPService {
    
    /**
     * 开启某个端口的监听
     */
    public void startHTTPService(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("监听端口" + port);
        Socket socket = null;
        while (serverSocket != null) {
            try {
                socket = serverSocket.accept();
                System.out.println("建立TCP连接，地址：" + socket.getInetAddress() + ":" + socket.getPort());
                service(socket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void service(Socket socket) throws IOException, InterruptedException {
        InputStream socketIn = socket.getInputStream();
        Thread.sleep(500);
        int size = socketIn.available();
        byte buffer[] = new byte[size];
        socketIn.read(buffer);
        String request = new String(buffer);
        System.out.println(request);
        
        String firstLineOfRequest = request.substring(0, request.indexOf("\r\n"));
        
        String parts[] = firstLineOfRequest.split(" ");
        String uri = parts[1];
        
        String contentType;
        if (uri.indexOf("html") != -1 || uri.indexOf("htm") != -1) {
            contentType = "text/html";
        } else if (uri.indexOf("jpg") != -1 || uri.indexOf("jpeg") != -1) {
            contentType = "image/jpeg";
        } else if (uri.indexOf("gif") != -1) {
            contentType = "image/gif";
        } else {
            contentType = "application/octet-stream";
        }
        
        String responseFirstLine = "HTTP/1.1 200 OK\r\n";
        
        String responseHeader = "Content-Type:" + contentType + "\r\n\r\n";
        
        InputStream in = HTTPService.class.getResourceAsStream(uri);
        
        OutputStream socketOut = socket.getOutputStream();
        
        socketOut.write(responseFirstLine.getBytes());
        socketOut.write(responseHeader.getBytes());
        
        int len = 0;
        buffer = new byte[128];
        while ((len = in.read(buffer)) != -1) {
            socketOut.write(buffer,0,len);
        }
        // String responseContent = "成功";
        // socketOut.write(responseContent.getBytes());
        Thread.sleep(1000);
        socket.close();
        
    }
}
