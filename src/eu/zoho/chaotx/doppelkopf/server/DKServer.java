package eu.zoho.chaotx.doppelkopf.server;

import eu.zoho.chaotx.doppelkopf.server.connect.Protocol;
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
                new Thread(() -> SessionManager.addClient(client)) {{
                    setDaemon(true);
                }}.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}