package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MultithreadedServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(666);

        new Thread(() -> {
            while (true) {
                try {
                    final Socket client = serverSocket.accept();
                    Session session = new Session(client);
                    SessionStorage.getTheOne().addSession(session);
                    session.start();
                    //new Session(client).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class Session extends Thread {
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;
    private SessionStorage sessionStorage;
    private static HistoryLog logger;

    static {
        try {
            logger = new HistoryLog();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Session(Socket client) throws IOException {
        this.client = client;
        sessionStorage = SessionStorage.getTheOne();

        in = new BufferedReader(
                new InputStreamReader(
                        new BufferedInputStream(
                                client.getInputStream())));
//        out = new BufferedWriter(
//                new OutputStreamWriter(
//                        new BufferedOutputStream(
//                                client.getOutputStream())));
    }

    public Socket getClient() {
        return client;
    }

    private BufferedWriter getClientOutBuffer(Socket client) throws IOException {
        return new BufferedWriter(
                new OutputStreamWriter(
                        new BufferedOutputStream(
                                client.getOutputStream())));
    }

    @Override
    public synchronized void run() {
        while (true) {
            try {
                //String message = in.readLine();
                ChatMessageHandler messageHandler = new ChatMessageHandler(in.readLine());
                logger.log(messageHandler.getInfoMessage());
                for (Session session : sessionStorage.getSessions()) {
                    BufferedWriter out = getClientOutBuffer(session.getClient());
                    out.write(">>> " + messageHandler.getInfoMessage());
                    out.newLine();
                    out.flush();
//                sessionStorage.getSessions().forEach(session -> {
//                    BufferedWriter out = getClientOutBuffer(session.getClient());
//                    String message = in.readLine();
//                    out.write(">>> " + message);
//                    out.newLine();
//                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
