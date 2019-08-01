package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;

public class Server {
    private static HistoryLog logger;
    static {
        try {
            logger = new HistoryLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //private static ChatMessageHandler messageHandler = new ChatMessageHandler;


    public static void main(String[] args) {
        try (final ServerSocket serverSocket = new ServerSocket(666)) {
            while (true) {
                Socket client = serverSocket.accept();

                try (BufferedReader in =
                             new BufferedReader(
                                     new InputStreamReader(
                                             new BufferedInputStream(
                                                     client.getInputStream())));
                     final BufferedWriter out =
                             new BufferedWriter(
                                     new OutputStreamWriter(
                                             new BufferedOutputStream(
                                                     client.getOutputStream())))) {

                    while (true) {
                        ChatMessageHandler messageHandler = new ChatMessageHandler(in.readLine());
                        String infoMessage =  messageHandler.getInfoMessage();
                        out.write(infoMessage);
                        logger.log(infoMessage);
                        out.newLine();
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
