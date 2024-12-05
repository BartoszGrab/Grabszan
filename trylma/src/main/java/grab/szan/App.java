package grab.szan;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Server server = Server.getInstance();
        server.start();
    }
}
