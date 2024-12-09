package grab.szan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String host;
    private final int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected to the server.");

            new Thread(this::listenForMessages).start();
            handleUserInput();
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        } finally {
            closeConnections();
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Server: " + message);
            }
        } catch (IOException e) {
            System.err.println("Connection lost: " + e.getMessage());
        }
    }

    private void handleUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Type 'help' for more iformation");

            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input)) {
                    break;
                }
                else if ("help".equalsIgnoreCase(input)) {
                    System.out.println("Type 'exit' to close the connection.");
                    System.out.println("Type 'move <start> <end>' to make a move.");
                    System.out.println("Type 'join <game_name>' to join a game.");
                    System.out.println("Type 'create <game_name> <num_of_players>' to create a game.");
                    continue;
                }
                out.println(input);
            }
        }
    }

    private void closeConnections() {
        try {
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }
}
