package grab.szan.command_tests;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import grab.szan.Game;
import grab.szan.GameState;
import grab.szan.Player;
import grab.szan.Server;
import grab.szan.commands.MoveCommand;

/**
 * Test class for the MoveCommand.
 * 
 * For testing purposes we use fake implementations:
 * - FakeGame – a simplified game that always accepts the move and collects broadcast messages.
 * - FakePlayer – a simplified player that records sent messages.
 */
public class MoveCommandTest {

    // FakeGame: minimal implementation of Game for testing purposes.
    // (We do not need full board functionality; we only simulate moveCurrentPlayer and broadcast.)
    private static class FakeGame extends Game {
        public List<String> broadcasts = new ArrayList<>();
        private Player currentPlayer;
        
        public FakeGame(String room, int maxPlayers, String gameType) {
            super(room, maxPlayers, gameType);
        }
        
        public void setCurrentPlayer(Player p) {
            currentPlayer = p;
        }
        
        @Override
        public Player getCurrentPlayer() {
            return currentPlayer;
        }
        
        @Override
        public boolean moveCurrentPlayer(int startRow, int startCol, int endRow, int endCol) {
            // For testing, we assume the move is valid and executed.
            return true;
        }
        
        @Override
        public void broadcast(String message) {
            broadcasts.add(message);
        }

        @Override
        public GameState getState() {
            return GameState.STARTED;
        }
    }

    // FakePlayer: minimal implementation of Player to record sent messages.
    private static class FakePlayer extends Player {
        public List<String> sentMessages = new ArrayList<>();
        private Game activeGame;
        private int id;
        
        public FakePlayer() {
            super(null); // No real socket needed.
        }
        
        @Override
        public void sendMessage(String message) {
            sentMessages.add(message);
        }
        
        @Override
        public Game getActiveGame() {
            return activeGame;
        }
        
        @Override
        public void setActiveGame(Game game) {
            activeGame = game;
        }
        
        @Override
        public int getId() {
            return id;
        }
        
        @Override
        public void setId(int id) {
            this.id = id;
            // We can choose to record the setId call or ignore it.
            // For our test we ignore broadcasting of setId.
        }
    }
    
    @Before
    public void setUp() {
        // Reset the Server singleton before each test.
        Server.resetInstance();
    }

    // Test 1: Too few arguments.
    @Test
    public void testTooFewArguments() {
        FakePlayer player = new FakePlayer();
        MoveCommand command = new MoveCommand();
        // Provide only three arguments – required are 5.
        String[] args = {"move", "1", "2"};
        command.execute(args, player);
        
        assertFalse("Expected an error message due to too few arguments", player.sentMessages.isEmpty());
        String response = player.sentMessages.get(0);
        assertTrue("Expected usage error for move command",
                response.contains("display Error too few arguments. Usage: move <start_row> <start_col> <end_row> <end_col>."));
    }
    
    // Test 2: Player not part of any game.
    @Test
    public void testNoActiveGame() {
        FakePlayer player = new FakePlayer();
        MoveCommand command = new MoveCommand();
        String[] args = {"move", "1", "2", "3", "4"};
        // Active game remains null.
        command.execute(args, player);
        
        assertFalse("Expected an error message because player has no active game", player.sentMessages.isEmpty());
        String response = player.sentMessages.get(0);
        assertTrue("Expected error indicating the player is not part of any game",
                response.contains("display Error you are currently not part of any game!"));
    }
    
    // Test 3: Not player's turn.
    @Test
    public void testNotYourTurn() {
        FakePlayer player = new FakePlayer();
        FakeGame game = new FakeGame("testRoom", 2, "Classic");
        player.setActiveGame(game);
        // Set currentPlayer to a different player.
        FakePlayer otherPlayer = new FakePlayer();
        game.setCurrentPlayer(otherPlayer);
        
        MoveCommand command = new MoveCommand();
        String[] args = {"move", "1", "2", "3", "4"};
        command.execute(args, player);
        
        assertFalse("Expected an error message because it's not the player's turn", player.sentMessages.isEmpty());
        String response = player.sentMessages.get(0);
        assertTrue("Expected error message indicating it's not your turn",
                response.contains("display Error it's not your turn!"));
    }
    
    // Test 4: Non-integer arguments.
    @Test
    public void testNonIntegerArguments() {
        FakePlayer player = new FakePlayer();
        FakeGame game = new FakeGame("testRoom", 2, "Classic");
        player.setActiveGame(game);
        // Set player as the current player.
        game.setCurrentPlayer(player);
        
        MoveCommand command = new MoveCommand();
        String[] args = {"move", "a", "2", "3", "4"};
        command.execute(args, player);
        
        assertFalse("Expected an error message due to non-integer argument", player.sentMessages.isEmpty());
        String response = player.sentMessages.get(0);
        assertTrue("Expected error message stating that arguments should be integers",
                response.contains("arguments should be integers"));
    }
    
    // Test 5: Successful move.
    @Test
    public void testSuccessfulMove() {
        FakePlayer player = new FakePlayer();

        player.setId(7);
        FakeGame game = new FakeGame("testRoom", 2, "Classic");
        player.setActiveGame(game);

        game.setCurrentPlayer(player);
        
        player.sentMessages.clear();
        game.broadcasts.clear();
        
        MoveCommand command = new MoveCommand();

        String[] args = {"move", "1", "2", "3", "4"};
        command.execute(args, player);
        
        List<String> bcasts = game.broadcasts;
        assertEquals("Expected two broadcast messages", 2, bcasts.size());
        String bcast1 = bcasts.get(0);
        String bcast2 = bcasts.get(1);
        assertTrue("First broadcast should be: set 1 2 6", bcast1.equals("set 1 2 6"));
        assertTrue("Second broadcast should be: set 3 4 7", bcast2.equals("set 3 4 7"));
        
        // Dodatkowo, w przypadku poprawnego ruchu MoveCommand nie wysyła bezpośrednio wiadomości do gracza.
        assertTrue("No direct message expected for a successful move", player.sentMessages.isEmpty());
    }
}
