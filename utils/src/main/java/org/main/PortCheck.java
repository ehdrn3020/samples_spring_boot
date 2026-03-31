package org.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* PID 점유해서 다른 서버에서 방화벽 접속 확인 */
public class PortCheck {
    public static void main(String[] args) {
        int port = 8083;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on port " + port);
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Accepted connection from " + client.getRemoteSocketAddress());
                client.close();
            }
        } catch (IOException e) {
            System.err.println("Failed to bind port " + port);
            e.printStackTrace();
        }
    }
}
