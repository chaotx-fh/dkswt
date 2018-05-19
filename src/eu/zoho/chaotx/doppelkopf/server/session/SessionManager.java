package eu.zoho.chaotx.doppelkopf.server.session;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import eu.zoho.chaotx.doppelkopf.server.connect.Protocol;


public class SessionManager {
    private static final int MAX_USER = 2;
    private static final User[] room = new User[MAX_USER];
    private static final Protocol protocol = new Protocol();

    static {
        initProtocol();
    }

    public static void addClient(Socket client) {
        protocol.getHandler("dk_connect").handle(client);
    }

    // TODO serialize
    private static void initProtocol() {
        protocol.setHandler("dk_connect", (client) -> {
            String username, password;

            try(Scanner sc = new Scanner(client.getInputStream())) {
                username = sc.nextLine();
                password = sc.nextLine();
            } catch(IOException e) {
                e.printStackTrace();
                username = "anonymous";
                password = "1234";
            }

            // TODO validate user (db)

            int index = 0;
            for(; index < room.length && room[index] != null; ++index);
            room[index] = new User(username, password, client);
            System.out.println("server: " + username + " entered waiting room");
    
            if(index+1 == MAX_USER) {
                System.out.println("server: room full, starting game");
    
                new Thread(new Session(room)) {{
                    setDaemon(true);
                }}.start();
                Arrays.fill(room, null);
            }
        });
    }
}