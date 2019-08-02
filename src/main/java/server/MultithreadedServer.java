package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultithreadedServer {
    public static HistoryLog logger;

    static {
        try {
            logger = new HistoryLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(666);

        new Thread(() -> {
            while (true) {
                try {
                    final Socket client = serverSocket.accept();
                    Session session = new Session(client);
                    SessionStorage.getTheOne().addSession(session);
                    session.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            try {
                while (Thread.activeCount() > 1) {
                }
                logger.closeFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
