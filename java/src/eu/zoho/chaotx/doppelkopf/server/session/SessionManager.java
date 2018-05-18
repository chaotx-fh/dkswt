package eu.zoho.chaotx.doppelkopf.server.session;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class SessionManager {
    private static final int MAX_CLIENTS = 2;
    private static final User[] room = new User[MAX_CLIENTS];
    private static final List<Session> sessions = new LinkedList<>();


    public static void addSession(Session session) {
        sessions.add(session);
    }

    public static void addClient(Socket client, String user) {
        final Socket final_client = client;

        int index = 0;
        for(; index < room.length && room[index] != null; ++index);

        room[index] = new User(user, () -> {
            try {
                return final_client.getInputStream().read();
            } catch(IOException e) {
                e.printStackTrace();
                return -1;
            }
        });

        if(index+1 == MAX_CLIENTS) {
            //sessions.add(new Session(room));
            new Session(room).start();
            Arrays.fill(room, null);
        }
    }

    public static void runSessions() {
        for(Session session : sessions) {
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