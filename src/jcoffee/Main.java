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
    private ArrayList<PrintWriter> list_clientWriter;
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
            list_clientWriter = new ArrayList<>();
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
                list_clientWriter.add(new PrintWriter(client.getOutputStream()));
                new Thread(new ClientHandler(client)).start();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class ClientHandler implements Runnable {

        private Socket client;
        private BufferedReader reader;

        public ClientHandler(Socket client) {
            try {
                this.client = client;
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } catch (IOException ex) {
            }
        }

        @Override
        public void run() {
            try {
                String command = "";

                while ((command = reader.readLine()) != null) {
                    if (command.equals("Kaffee machen")) {
                        new Commander().makeCoffee();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private class Commander implements Runnable {

        private void makeCoffee() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            if (!isMakingCoffee) {
                try {
                    isMakingCoffee = true;
                    //Kaffee-Mach-Methoden hier schreiben
                    System.out.println("Kaffee wird gemacht");
                    Thread.sleep(50000);
                    isMakingCoffee = false;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Es wird bereits ein kaffee gemacht!");
            }
        }
    }

}
