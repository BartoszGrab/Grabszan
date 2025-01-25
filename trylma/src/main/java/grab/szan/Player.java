package grab.szan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import grab.szan.commands.Command;
import grab.szan.commands.CommandHandler;

/**
 * Represents a player in the game. Handles communication with the client through a socket connection.
 */
public class Player implements Runnable {

    protected int id;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Game activeGame;
    private String nickname;

    private final CommandHandler commandHandler;

    /**
     * Main constructor for a real human player's connection.
     *
     * @param socket         the client socket
     * @param commandHandler the command handler
     */
    public Player(Socket socket, CommandHandler commandHandler) {
        this.socket = socket;
        this.commandHandler = commandHandler;
    }

    /**
     * Secondary constructor for bots (no socket required).
     *
     * @param commandHandler the command handler
     */
    public Player(CommandHandler commandHandler) {
        this.socket = null; // no real socket
        this.commandHandler = commandHandler;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
        sendMessage("setId " + id);
    }

    public void sendMessage(String message) {
        out.println(message);
        out.flush();
    }

    public void setActiveGame(Game game){
        activeGame = game;
    }

    public Game getActiveGame(){
        return activeGame;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            while (true) {
                String commandStr = in.readLine();
                if (commandStr == null) break;

                String[] args = commandStr.split(" ");
                try {
                    Command command = commandHandler.getCommand(args[0]);
                    command.execute(args, this);
                } catch (IllegalArgumentException e) {
                    this.sendMessage("display " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
