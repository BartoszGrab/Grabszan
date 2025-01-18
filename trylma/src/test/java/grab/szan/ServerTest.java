package grab.szan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ServerTest {
    @Before
    public void cleanupSingletonReference(){
        Server.resetInstance();
    }

    @Test
    public void testMultiThreading(){
        List<Thread> threads = new ArrayList<>();
        List<Server> references = Collections.synchronizedList(new ArrayList<Server>());

        for(int i = 0; i < 10; i++){
            Thread worker = new Thread(new Worker(i, references));
            threads.add(worker);
            worker.start();
        }

        for(Thread thread : threads){
            while (thread.isAlive()) {
                //czekamy az wątek zginie
            }
        }

        //zakładamy że wszystkie odniesienia prowadzą do tego samego obiektu
        for(Server server: references){
            Server expected = server;
            for(Server server2 : references){
                assertEquals(expected, server2);
            }
        }
    }

    @Test
    public void testGetSingleton(){
        assertNotNull(Server.getInstance());
    }

    @Test
    public void testServerClientIntegration() throws IOException, InterruptedException {
        // rozpoczecie serwera na osobnym wątku
        Thread serverThread = new Thread(() -> {
            Server server = Server.getInstance();
            server.start();
        });
        serverThread.start();
    
        //czas zeby serwer sie odpalil
        Thread.sleep(1000);
    
        // podłączenie klienta
        try (Socket clientSocket = new Socket("localhost", 3006)) {
            clientSocket.setSoTimeout(5000); // timeout 
    
            // Write to server
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    
            // wyslij cos do serwera
            out.println("t");
    
            // odczytanie odpowiedzi od serwera
            String message = in.readLine(); 
            // sprawdzenie poprawnosci odpowiedzi
            assertNotNull(message);
            assertTrue(message.contains("invalid command line"));
        } catch (SocketTimeoutException e) {
            fail("Server did not respond in time");
        } finally {
            // Stop server
            Server.getInstance().stop();
            serverThread.join();
        }
    }
    


    class Worker implements Runnable {

		private int number;
		private List<Server> myReferences;

		public Worker(int number, List<Server> references) {
			this.setNumber(number);
			this.myReferences = references;

		}

		@Override
		public void run() {
			// Dummy call
			this.myReferences.add(Server.getInstance());

		}

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

	}
}
