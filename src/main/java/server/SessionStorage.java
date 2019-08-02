package server;

import java.util.*;

public class SessionStorage {
    private Collection<Session> sessionList =  new LinkedList<>();

    private SessionStorage() {
        //Collection<Session> sessionList = new LinkedList<>();
    }

    public Collection<Session> getSessions() {
        return sessionList;
    }

    public Collection<Session> getWriterSessions() {
        getSessions().
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
