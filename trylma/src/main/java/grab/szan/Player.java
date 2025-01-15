package grab.szan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import grab.szan.commands.CommandHandler;

public class Player implements Runnable{
    private int id;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Game activeGame;
    private String nickname;

    public String getNickname(){
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

    public Player(Socket socket){
        this.socket = socket;
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
