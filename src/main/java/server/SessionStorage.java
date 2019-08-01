package server;

import java.util.ArrayList;
import java.util.List;

public class SessionStorage {
    private List<Session> sessionList = new ArrayList<>();

    private SessionStorage() {
    }

    public List<Session> getSessions() {
        return sessionList;
    }

    public void addSession(Session session) {
        sessionList.add(session);
    }

    //=========================

    private static SessionStorage theOne = new SessionStorage();

    public static SessionStorage getTheOne() {
        return theOne;
    }
}
