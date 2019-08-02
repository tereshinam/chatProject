package server;

import java.util.*;
import java.util.stream.Collectors;

public class SessionStorage {
    private Collection<Session> sessionList = new LinkedList<>();

    private SessionStorage() {
    }

    public Collection<Session> getSessions() {
        return sessionList;
    }

    public Collection<Session> getWriterSessions() {
        return getSessions().stream()
                .filter(stream -> !stream.isReader())
                .collect(Collectors.toList());
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
