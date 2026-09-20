package org.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* 포트가 실제로 열려서 외부 접속을 받을 수 있는지 확인하는 단순 테스트 */
/* javac PortCheck.java -> java PortCheck 로 실행 */
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
