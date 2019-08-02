package server;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

class Session extends Thread {
    private Socket client;
    private BufferedReader in;
    private BufferedWriter out;
    private SessionStorage sessionStorage;
    private String username = "Anonymous";
    private int clientId;
    private boolean isReader;
    private static HistoryLog logger;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    private void unicast(Iterator<Session> sessionIterator, ChatMessageHandler messageHandler) {
        Session session = sessionIterator.next();
        try {
            BufferedWriter out = getClientOutBuffer(session.getClient());
            //String message = messageHandler.toString();
            //String[] lineOfMessage = message.split("\n");
            //for(String line: lineOfMessage) {
                out.write(messageHandler.toString());
                out.newLine();
                out.write("");
                out.newLine();
                out.flush();
            //}
        } catch (Exception e) {
            sessionIterator.remove();
        }
    }

    private void broadcast(ChatMessageHandler messageHandler) {
        synchronized (this) {
            Iterator<Session> sessionIterator = sessionStorage.getSessions().iterator();
            while (sessionIterator.hasNext()) {
                unicast(sessionIterator, messageHandler);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                ChatMessageHandler messageHandler = new ChatMessageHandler(in.readLine());
                LinkedList<Session> list = new LinkedList<>();
                list.add(this);
                switch (messageHandler.getType()){
                    case SND:
                        messageHandler.setName(username);
                        broadcast(messageHandler);
                        logger.log(messageHandler.getInfoMessage());
                        break;
                    case HIST:
                        unicast(list.iterator(), messageHandler);
                        break;
                    case CHILD:
                        username = messageHandler.getName();
                        unicast(list.iterator(), messageHandler);
                        break;
                    case READER:
                        isReader = true;
                        clientId = messageHandler.getUserId();
                        break;
                    case WRITER:
                        isReader = false;
                        clientId = messageHandler.getUserId();
                        break;
                    default:
                        unicast(list.iterator(), messageHandler);
                        break;
                }
            } catch (Exception e) {

            }
        }
    }
}

