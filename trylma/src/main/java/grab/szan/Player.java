package grab.szan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import grab.szan.commands.CommandHandler;

public class Player implements Runnable{
    private char mark;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public char getMark(){
        return mark;
    }

    public Player(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            out.println("Welcome to server!");

            while(true){
                String command = in.readLine();
                if(command == null) break;

                String[] args = command.split(" ");

                CommandHandler.getCommandHandler().getCommand(args[0]).execute(args);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
