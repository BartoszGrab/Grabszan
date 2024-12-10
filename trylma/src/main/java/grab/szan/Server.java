package grab.szan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/*
 * klasa reprezentujaca serwer, ktory bedzie wykorzystywal wzorzec singleton
 */
public class Server {
    
    private static volatile Server server_instance;

    /*mapowanie <nazwa gry> z <obiekt typu Game> w celu latwego wyszukiwania dostepnych pokojow */
    private static Map<String, Game> games = new HashMap<>();
    private static final int port = 3006;
    private ServerSocket serverSocket;
    private boolean isRunning;

    //prywatny konstruktor, aby tylko sama klasa miala do niego dostep
    private Server(){

    }

    /*metoda do pozyskiwania instancji obiektu Server */
    public static Server getInstance(){
        Server localServer = server_instance;

        //double-checked locking zeby zapobiedz stworzeniu 2 instancji
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

    /*metoda do startowania serwera */
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

    /*metoda do stopowania serwera */
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

    public boolean gameExists(String room){
        return games.containsKey(room);
    }

    public boolean addGame(Game game){
        if(games.containsKey(game.getRoom())) return false;
        
        games.put(game.getRoom(), game);
        return true;
    } 

    public Game getGame(String room) {
        return games.get(room);
    }
    
    public static void resetInstance(){
        server_instance = null;
    }
    
}
