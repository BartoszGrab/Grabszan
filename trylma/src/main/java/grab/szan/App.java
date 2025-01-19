package grab.szan;

/**
 * Main application class that starts the server.
 */
public class App 
{
    /**
     * The main method to run the application and start the server.
     */
    public static void main(String[] args)
    {
        Server server = Server.getInstance();
        server.start();
    }
}
