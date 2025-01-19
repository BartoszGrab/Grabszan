package grab.szan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import grab.szan.commands.CommandHandler;
import grab.szan.utils.Utils;

/**
 * Represents a client that connects to the game server.
 * Manages input/output streams, the socket connection, and command handling.
 */
public class Client {
    private static Client instance;

    /**
     * The server's host address.
     */
    private final String host;

    /**
     * The server's port number.
     */
    private final int port;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * The ID assigned to this client, typically corresponding to the player's piece ID or corner ID.
     */
    private int id;

    /**
     * The name of the room (game) this client is connected to.
     */
    private String roomName;

    /**
     * The nickname chosen by the client.
     */
    private String nickname;

    /**
     * Private constructor to enforce singleton usage.
     *
     * @param host the server's host
     * @param port the server's port
     */
    private Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Starts the client by attempting to connect to the server.
     * Spawns a new thread to listen for messages from the server.
     *
     * @return true if the connection is successful, false otherwise
     */
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

    /**
     * Continuously listens for messages from the server and handles them using {@link CommandHandler}.
     */
    private void listenForMessages() {
        try {
            while (true) {
                String command = in.readLine();
                if(command == null) break;

                String[] args = command.split(" ");

                try {
                    CommandHandler.getCommandHandler().getCommand(args[0]).execute(args);
                } catch(IllegalArgumentException e){
                    Utils.showAlert("Error", e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Connection lost: " + e.getMessage());
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param msg the message to send
     */
    public void sendToServer(String msg) {
        out.println(msg);
    }

    /**
     * Gets the current ID of this client.
     *
     * @return the client's ID
     */
    public int getId(){
        return id;
    }

    /**
     * Sets the client's ID.
     *
     * @param id the new ID
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Gets the current room name.
     *
     * @return the room name
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Sets the current room name.
     *
     * @param roomName the new room name
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Gets the client's nickname.
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the client's nickname.
     *
     * @param nickname the new nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Closes the socket and associated input/output streams.
     */
    private void closeConnections() {
        try {
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }

    /**
     * Creates a new client instance if one does not already exist.
     * Otherwise, retains the existing instance.
     *
     * @param host the server's host
     * @param port the server's port
     */
    public static void createClient(String host, int port){
        if(instance == null){
            instance = new Client(host, port);
        }
    }

    /**
     * Retrieves the singleton client instance.
     *
     * @return the existing client instance
     * @throws NullPointerException if the instance does not exist
     */
    public static Client getInstance() throws NullPointerException {
        if(instance == null){
            throw new NullPointerException("instance does not exist");
        }
        return instance;
    }
}
