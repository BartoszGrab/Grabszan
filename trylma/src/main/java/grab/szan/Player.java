package grab.szan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import grab.szan.commands.CommandHandler;

/**
 * Represents a player in the game. Handles communication with the client through a socket connection.
 */
public class Player implements Runnable {

    /**
     * The unique identifier for this player.
     */
    protected int id;

    /**
     * The socket used to communicate with the client.
     */
    private Socket socket;

    /**
     * The input stream for reading messages from the client.
     */
    private BufferedReader in;

    /**
     * The output stream for sending messages to the client.
     */
    private PrintWriter out;

    /**
     * The current game in which this player is participating.
     */
    private Game activeGame;

    /**
     * The nickname chosen by this player.
     */
    private String nickname;

    /**
     * Constructs a player object using the given client socket.
     *
     * @param socket the client socket for communication
     */
    public Player(Socket socket){
        this.socket = socket;
    }

    /**
     * Gets the nickname of this player.
     *
     * @return the player's nickname
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * Sets the nickname of this player.
     *
     * @param nickname the new nickname
     */
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * Gets the unique identifier of this player.
     *
     * @return the player's ID
     */
    public int getId(){
        return id;
    }

    /**
     * Sets the unique identifier of this player and notifies the client.
     *
     * @param id the new ID to set
     */
    public void setId(int id){
        this.id = id;
        sendMessage("setId " + id);
    }

    /**
     * Sends a message to the client.
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }

    /**
     * Sets the active game for this player.
     *
     * @param game the game to set as active
     */
    public void setActiveGame(Game game){
        activeGame = game;
    }

    /**
     * Gets the active game for this player.
     *
     * @return the currently active game
     */
    public Game getActiveGame(){
        return activeGame;
    }

    /**
     * The main loop for handling incoming commands from the client.
     */
    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            while(true){
                String command = in.readLine();
                if(command == null) break;

                String[] args = command.split(" ");
                try{
                    CommandHandler.getCommandHandler().getCommand(args[0]).execute(args, this);
                } catch(IllegalArgumentException exception){
                    this.sendMessage(exception.getMessage());
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
