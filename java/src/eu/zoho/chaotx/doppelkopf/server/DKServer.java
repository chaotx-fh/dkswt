package eu.zoho.chaotx.doppelkopf.server;

import eu.zoho.chaotx.doppelkopf.server.session.SessionManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

class DKServer {
    public static void main(String[] args) {
        System.out.println("Server running");

        try(ServerSocket server = new ServerSocket(80)) {
            while(true) {
                Socket client = server.accept();
                new Thread(() -> handleConnection(client)) {{
                    setDaemon(true);
                }}.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket client) {
        try(Scanner sc = new Scanner(client.getInputStream())) {
            String user = sc.hasNextLine() ? sc.nextLine() : "anonymous";
            String password = sc.hasNextLine() ? sc.nextLine() : "1234";
            System.out.println("server: Hello " + user + ", your password is " + password);

            SessionManager.addClient(client, user);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}