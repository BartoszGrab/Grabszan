package grab.szan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the server, implemented as a singleton pattern.
 */
public class Server {
    
    /**
     * The singleton instance of the Server.
     */
    private static volatile Server server_instance;

    /**
     * Maps the game name to the corresponding Game object for easy lookup.
     */
    private static Map<String, Game> games = new HashMap<>();

    /**
     * The port number on which the server listens.
     */
    private final int port = 3006;

    /**
     * The ServerSocket for accepting client connections.
     */
    private ServerSocket serverSocket;

    /**
     * Indicates whether the server is currently running.
     */
    private boolean isRunning;

    /**
     * Private constructor to prevent instantiation from outside this class.
     */
    private Server(){
    }

    /**
     * Gets the singleton instance of the Server.
     * Uses double-checked locking to ensure only one instance is created.
     *
     * @return the Server instance
     */
    public static Server getInstance(){
        Server localServer = server_instance;

        if(localServer == null){
            synchronized(Server.class){
                localServer = server_instance;
                if(localServer == null){
                    server_instance = localServer = new Server();
                }
            }
        }
        return server_instance;
    }

    /**
     * Starts the server, listening for client connections.
     */
    public void start(){
        try {
            serverSocket = new ServerSocket(port);
            isRunning = true;
            System.out.println("Server started on port " + port);

            while(isRunning){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                new Thread(new Player(clientSocket)).start();
            }
        } catch(IOException e){
            e.printStackTrace();
            stop();
        }
    }

    /**
     * Stops the server by closing the server socket.
     */
    public void stop(){
        try {
            isRunning = false;
            if(serverSocket != null){
                serverSocket.close();
            }
            System.out.println("Server stopped.");
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Checks whether a game with the specified room name exists.
     *
     * @param room the name of the game room
     * @return true if the game exists, false otherwise
     */
    public boolean gameExists(String room){
        return games.containsKey(room);
    }

    /**
     * Adds a new game to the server's list of available games.
     *
     * @param game the Game object to be added
     * @return true if the game was added successfully, false otherwise
     */
    public boolean addGame(Game game){
        if(games.containsKey(game.getRoom())) return false;

        games.put(game.getRoom(), game);
        return true;
    }

    /**
     * Retrieves a game by its room name.
     *
     * @param room the name of the game room
     * @return the Game object, or null if not found
     */
    public Game getGame(String room) {
        return games.get(room);
    }

    /**
     * Resets the server instance to null. Intended for testing or re-initialization.
     */
    public static void resetInstance(){
        server_instance = null;
    }

    /**
     * Gets the port on which the server is running.
     *
     * @return the port number
     */
    public int getPort(){
        return port;
    }
}
