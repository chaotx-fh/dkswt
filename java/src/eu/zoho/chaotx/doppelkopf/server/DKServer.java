package eu.zoho.chaotx.doppelkopf.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

class DKServer {
    public static void main(String[] args) {
        /*
        try(ServerSocket server = new ServerSocket(58089)) {
            while(true) {
                Socket client = server.accept();
                new Thread(() -> handleConnection(client)) {{
                    setDaemon(true);
                }}.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        */
        System.out.println("Hello Mothafucka");
    }

    private static void handleConnection(Socket client) {
        int cmd;

        try(InputStream is = client.getInputStream()) {
            while((cmd = is.read()) >= 0)
                System.out.print(cmd + ",");

            System.out.println();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}