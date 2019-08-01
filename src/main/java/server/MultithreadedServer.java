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
                    //new Session(client).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(()->{
            try {
                while(Thread.activeCount()>1){}
                logger.closeFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

class Session extends Thread {
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;
    private SessionStorage sessionStorage;


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
    public void run() {
        while (true) {
            try {
                //String message = in.readLine();
                ChatMessageHandler messageHandler = new ChatMessageHandler(in.readLine());
                switch (messageHandler.getType()){
                    case SND:
                        MultithreadedServer.logger.log(messageHandler.getInfoMessage());
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
                        break;
                    case HIST:
                        out = new BufferedWriter(
                                new OutputStreamWriter(
                                        new BufferedOutputStream(
                                                client.getOutputStream())));
                        out.write(MultithreadedServer.logger.getHistory());
                        out.newLine();
                        out.flush();
                        break;
                    case NONE:
                        out = new BufferedWriter(
                                new OutputStreamWriter(
                                        new BufferedOutputStream(
                                                client.getOutputStream())));
                        out.write("Not a command");
                        out.newLine();
                        out.flush();
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
