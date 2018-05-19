package eu.zoho.chaotx.doppelkopf.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class DKClient extends Application {
    public static void main(String[] args) {
        //* Client Test
        Scanner scanner = new Scanner(System.in);
        String user, password;

        System.out.print("Enter username: ");
        user = scanner.nextLine();

        System.out.print("Enter password: ");
        password = scanner.nextLine();

        try(Socket server = new Socket("localhost", 80)) {
            PrintWriter pw = new PrintWriter(server.getOutputStream(), true);
            pw.println(user);
            pw.println(password);

            int response;
            boolean running = true;
            while(running) {
                response = server.getInputStream().read();

                if(response == 0)
                    System.out.println("invalid play, try again");

                if(response == 1) {
                    System.out.print("Please choose card to play: ");
                    try {
                        server.getOutputStream().write(scanner.nextInt());
                    } catch(InputMismatchException e) {
                        server.getOutputStream().write(-1);
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        //*/


        System.out.println("fx baby");
        launch(args);
    }

    public void start(Stage ps) {
        Platform.exit();
    }

    /*
    public String getUser();
    public String getEmail();
    public String getPassword();
    public DKConnector getConnector();
    */
}