package grab.szan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import grab.szan.bots.BotNameGenerator;
import grab.szan.commands.CommandHandler;

/**
 * The Server bean is only responsible for listening on a socket
 * and creating Player threads. 
 * It does NOT store a map of games. 
 */
@Component
public class Server {

    private final int port = 3006;
    private ServerSocket serverSocket;
    private boolean isRunning;

    private final CommandHandler commandHandler;

    @Autowired
    public Server(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        BotNameGenerator.configure();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            isRunning = true;
            System.out.println("Server started on port " + port);

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");
                new Thread(new Player(clientSocket, commandHandler)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            stop();
        }
    }

    public void stop() {
        try {
            isRunning = false;
            if (serverSocket != null) {
                serverSocket.close();
            }
            System.out.println("Server stopped.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }
}
