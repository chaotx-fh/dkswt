package eu.zoho.chaotx.doppelkopf.server;

import java.util.ArrayList;
import java.util.List;


class SessionManager {
    private static List<DKSession> sessions;


    public static void addSession(DKSession session) {
        sessions.add(session);
    }

    public static void runSessions() {
        for(DKSession session : sessions) {
            switch(session.getState()) {
                case SUSPENDED:
                    session.start();
                    break;
                case RUNNING:
                    session.check();
                    break;
                case TERMINATED:
                    sessions.remove(session);
                    break;
            }
        }
    }
}