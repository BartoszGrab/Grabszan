package grab.szan.command_tests;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import grab.szan.Player;
import grab.szan.commands.Command;
import grab.szan.commands.SetNickCommand;

public class SetNickCommandTest {

    // Dummy implementation of Player for testing purposes.
    private static class FakePlayer extends Player {
        public List<String> messages = new ArrayList<>();
        private String nickname;
        
        public FakePlayer() {
            // No real socket required for testing.
            super(null);
        }
        
        @Override
        public void sendMessage(String message) {
            messages.add(message);
        }
        
        @Override
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
        
        @Override
        public String getNickname() {
            return nickname;
        }
    }
    
    private Command command;
    private FakePlayer player;
    
    @Before
    public void setUp() {
        // Prepare a fresh instance of SetNickCommand and fake player before each test.
        command = new SetNickCommand();
        player = new FakePlayer();
    }
    
    @Test
    public void testTooFewArguments() {
        // Provide only the command identifier, without a nickname.
        String[] args = {"nick"};
        command.execute(args, player);
        
        // We expect an error message prompting correct usage.
        assertFalse("Expected an error message due to too few arguments", player.messages.isEmpty());
        String errorMessage = player.messages.get(0);
        assertTrue("Expected error message containing the usage instructions",
                errorMessage.contains("Error: too few arguments. Usage: nick <nickname>"));
    }
    
    @Test
    public void testValidNick() {
        // Provide a valid nickname.
        String[] args = {"nick", "JohnDoe"};
        command.execute(args, player);
        
        // The player's nickname should be set to "JohnDoe".
        assertEquals("JohnDoe", player.getNickname());
        
        // In case of a successful operation, the command does not send any message.
        assertTrue("No error messages should be sent on successful nick change", player.messages.isEmpty());
    }
}
