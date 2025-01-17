package grab.szan.command_tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import grab.szan.Server;

public class CreateGameCommandTest {

    @Before
    public void setUp(){
        // Resetting the singleton instance so that each test starts with a fresh server.
        Server.resetInstance();
    }

    @Test
    public void tooFewArgumentsTest() throws IOException, InterruptedException {
        // Start the server on a separate thread.
        Thread serverThread = new Thread(() -> {
            Server server = Server.getInstance();
            server.start();
        });
        serverThread.start();

        // Wait a bit to ensure that the server has started.
        Thread.sleep(2000);

        // Create a real client connection to the server.
        try (Socket clientSocket = new Socket("localhost", 3006)) {
            clientSocket.setSoTimeout(5000);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Send an incomplete command: "create room 2"
            // (the command requires 5 arguments: command, room name, maxPlayers, nickname, gamemode).
            out.println("create room 2");

            // Read the server response
            String response = in.readLine();
            assertNotNull("Server response should not be null", response);
            assertTrue("Expected usage instruction for the create command.",
                response.contains("display Use: create <name> <num_of_players> <nickname> <gamemode>"));
        } catch (SocketTimeoutException e) {
            fail("Server did not respond in time (timeout).");
        } finally {
            Server.getInstance().stop();
            serverThread.join();
        }
    }

    @Test
    public void testCreateGameSuccess() throws IOException, InterruptedException {
        // Start the server on a separate thread.
        Thread serverThread = new Thread(() -> {
            Server server = Server.getInstance();
            server.start();
        });
        serverThread.start();

        // Wait a bit to ensure that the server has started.
        Thread.sleep(1000);

        // Create a real client connection to the server.
        try (Socket clientSocket = new Socket("localhost", 3006)) {
            clientSocket.setSoTimeout(5000);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Send a valid command with 5 arguments:
            // create <roomName> <maxPlayers> <nickname> <gamemode>
            out.println("create testRoom 2 testNick Classic");

            // Read three lines from the server, according to the order of the messages sent:
            // 1. setId message
            String setIdLine = in.readLine();
            assertNotNull("The first line should not be null", setIdLine);
            assertTrue("Expected message starting with setId", setIdLine.startsWith("setId"));

            // 2. acceptJoin message
            String acceptJoinLine = in.readLine();
            assertNotNull("Join game response should not be null", acceptJoinLine);
            assertTrue("Expected acceptJoin command with room name testRoom and gamemode Classic.",
                acceptJoinLine.startsWith("acceptJoin Classic testRoom"));

            // 3. display Success message
            String displaySuccessLine = in.readLine();
            assertNotNull("The success message should not be null", displaySuccessLine);
            assertTrue("Expected message indicating successful creation of game.",
                displaySuccessLine.contains("display Success created new game: testRoom with maximum number of players: 2"));
        } catch (SocketTimeoutException e) {
            fail("Server did not respond in time (timeout).");
        } finally {
            Server.getInstance().stop();
            serverThread.join();
        }
    }
}
