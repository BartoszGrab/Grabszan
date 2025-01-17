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

public class JoinGameCommandTest {

    @Before
    public void setUp() {
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
            clientSocket.setSoTimeout(10000);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            // Send an incomplete join command: only 2 arguments (command and room name).
            out.println("join testRoom");
            
            // Read the server response.
            String response = in.readLine();
            assertNotNull("Server response should not be null", response);
            assertTrue("Expected error message for too few arguments",
                response.contains("display Error Game name and nickname is required to join."));
        } catch (SocketTimeoutException e) {
            fail("Server did not respond in time (timeout).");
        } finally {
            Server.getInstance().stop();
            serverThread.join();
        }
    }
    
    @Test
    public void testJoinGameSuccess() throws IOException, InterruptedException {
        // Start the server on a separate thread.
        Thread serverThread = new Thread(() -> {
            Server server = Server.getInstance();
            server.start();
        });
        serverThread.start();
        
        // Wait a bit to ensure that the server has started.
        Thread.sleep(2000);
        
        // --- Phase 1: Create a game using the create command ---
        // We use a "creator" client to create the game "testRoom".
        try (Socket creatorSocket = new Socket("localhost", 3006)) {
            creatorSocket.setSoTimeout(10000);
            PrintWriter outCreator = new PrintWriter(creatorSocket.getOutputStream(), true);
            BufferedReader inCreator = new BufferedReader(new InputStreamReader(creatorSocket.getInputStream()));
            
            // Send a valid create command:
            // create <roomName> <maxPlayers> <nickname> <gamemode>
            outCreator.println("create testRoom 2 testNick1 Classic");
            
            String setIdLine = inCreator.readLine();
            // (We assume game creation is successful.)
        }
        
        // Wait a bit to ensure that game creation is processed.
        Thread.sleep(1000);
        
        // --- Phase 2: Join the game using the join command ---
        // Now, a new client ("joiner") will try to join the existing game "testRoom".
        try (Socket joinerSocket = new Socket("localhost", 3006)) {
            joinerSocket.setSoTimeout(10000);
            PrintWriter outJoiner = new PrintWriter(joinerSocket.getOutputStream(), true);
            BufferedReader inJoiner = new BufferedReader(new InputStreamReader(joinerSocket.getInputStream()));
            
            // Send a valid join command:
            // join <roomName> <nickname>
            outJoiner.println("join testRoom testNick2");
            Thread.sleep(2000);
            String setIdLine = inJoiner.readLine();
            assertNotNull("The first line should not be null", setIdLine);
            assertTrue("Expected message starting with setId", setIdLine.startsWith("setId"));
            // According to JoinGameCommand, if joining is successful, the following messages are sent:
            // 1. "display Success You have joined the game 'testRoom'."
            // 2. A broadcast message: "updateList <joinerNickname>" (sent to all players in the game)
            // 3. "acceptJoin <gameType> <roomName> <playerId> <players> <nickname>"
            String msg1 = inJoiner.readLine();
            String msg2 = inJoiner.readLine();
            String msg3 = inJoiner.readLine();
            // Check first message (success message).
            assertNotNull("First message should not be null", msg1);
            assertTrue("Expected success join message",
                msg1.contains("display Success You have joined the game 'testRoom'."));
            
            // Check second message (broadcast updateList).
            assertNotNull("Second message should not be null", msg2);
            assertTrue("Expected updateList broadcast message", msg2.startsWith("updateList"));
            
            // Check third message (acceptJoin message).
            assertNotNull("Third message should not be null", msg3);
            assertTrue("Expected acceptJoin message with room name testRoom and gamemode Classic.",
                msg3.startsWith("acceptJoin Classic testRoom"));
        } catch (SocketTimeoutException e) {
            fail("Server did not respond in time (timeout).");
        } finally {
            Server.getInstance().stop();
            serverThread.join();
        }
    }
}
