package grab.szan.command_tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import grab.szan.Server;

public class CreateGameCommandTest {

    @Before
    public void setUp(){
        Server.resetInstance();
    }

    @Test
    public void tooFewArgumentsTest(){
        Socket mockSocket = Mockito.mock(Socket.class);
        InputStream mockInput = new ByteArrayInputStream("create room".getBytes());
        OutputStream mockOutput = new ByteArrayOutputStream();

        try {
            Mockito.when(mockSocket.getInputStream()).thenReturn(mockInput);
            Mockito.when(mockSocket.getOutputStream()).thenReturn(mockOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }
    @Test
    public void createGameCommandTest() throws IOException, InterruptedException {
        // Start the server on a separate thread
        Thread serverThread = new Thread(() -> {
            Server server = Server.getInstance();
            server.start();
        });
        serverThread.start();
    
        // Allow server some time to start
        Thread.sleep(1000);
    
        // Connect a real client
        try (Socket clientSocket = new Socket("localhost", 3006)) {
            clientSocket.setSoTimeout(5000); // Set timeout to prevent blocking
    
            // Write to server
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    
            // Send a message to the server
            out.println("create room 2");
    
            // Read response from the server
            in.readLine(); 
            in.readLine();
            String message = in.readLine();
    
            // Validate server response
            assertNotNull(message);
            assertTrue(message.contains("Utworzono nową grę"));
        } catch (SocketTimeoutException e) {
            fail("Server did not respond in time");
        } finally {
            // Stop the server
            Server.getInstance().stop();
            serverThread.join();
        }
    }
}
