package grab.szan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import grab.szan.commands.CommandHandler;
import grab.szan.utils.Utils;

public class Client {
    private static Client instance;
    private final String host;
    private final int port;
    private int id;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // Metoda startująca klienta
    public boolean start() {
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to the server.");

            new Thread(this::listenForMessages).start();

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
            closeConnections();
            return false;
        } 
        
        return true;
    }

    // Nasłuchuje wiadomości od serwera
    private void listenForMessages() {
        try {
            while (true) {
                String command = in.readLine();
                if(command == null) break;

                String[] args = command.split(" ");

                try{
                    CommandHandler.getCommandHandler().getCommand(args[0]).execute(args);
                } catch(IllegalArgumentException e){
                    Utils.showAlert("Error", e.getMessage());
                }
                
            }
        } catch (IOException e) {
            System.err.println("Connection lost: " + e.getMessage());
        }
    }

    public void sendToServer(String msg) {
        out.println(msg);
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    // Zamyka połączenie z serwerem
    private void closeConnections() {
        try {
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }

    public static void createClient(String host, int port){
        if(instance == null){
            instance = new Client(host, port);
        }
    }

    public static Client getInstance() throws NullPointerException {
        if(instance == null){
            throw new NullPointerException("instance does not exist");
        }
        return instance;
    }
}