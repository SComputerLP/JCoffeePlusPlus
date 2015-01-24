package jcoffee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JCorn Development
 *
 * @author Julian Maierl
 */
public class Main {

    private static int PORT = 666;
    private ServerSocket server;
    //private ArrayList<PrintWriter> list_clientWriter;
    public static boolean isMakingCoffee;

    public static void main(String[] args) {

        Main main = new Main();
        if (main.startServer()) {
            main.listenForClients();
        }
    }

    private boolean startServer() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server wurde gestartet!");
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private void listenForClients() {
        while (true) {
            try {
                Socket client = server.accept();
                System.out.println(client.getInetAddress() + " hat sich verbunden");
                new Thread(new ClientHandler(client)).start();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
