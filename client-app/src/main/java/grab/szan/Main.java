package grab.szan;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar client-app.jar <host> <port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        Client client = new Client(host, port);
        client.start();
    }
}
