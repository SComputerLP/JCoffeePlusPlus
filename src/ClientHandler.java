import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * jCorn Development
 * @author Julian Maierl
 */
public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket client) {
        try {
            this.client = client;
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(client.getOutputStream());
        } catch (IOException ex) {
        }
    }

    @Override
    public void run() {
        try {
            String command = "";

            while ((command = reader.readLine()) != null) {
                System.out.println(command);
                if (command.equals("Kaffee machen")) {
                    if (!Main.isMakingCoffee) {
                        writer.write("Kaffee wird gemacht!\n");
                        writer.flush();
                        
                        Main.isMakingCoffee = true;
                        //Kaffee-mach-Methoden hier schreiben! (am Raspi)
                        Commander.makeCoffee();
                        Main.isMakingCoffee = false;
                    } else {
                        writer.write("Es wird bereits ein Kaffee gemacht!\n");
                        writer.flush();
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
