package grab.szan.commands;

import grab.szan.Game;
import grab.szan.Player;
import grab.szan.Server;

/**
 * A command that allows a player to join an existing game by name and set their nickname.
 */
public class JoinGameCommand implements Command {

    /**
     * Executes the join game command.
     * <p>
     * Usage: join &lt;roomName&gt; &lt;nickname&gt;
     *
     * @param args   the command arguments
     * @param player the player issuing the command
     */
    @Override
    public void execute(String[] args, Player player) {
        // Validate arguments
        if (args.length < 3) {
            player.sendMessage("display Error Game name and nickname is required to join.");
            return;
        }

        String roomName = args[1];
        Server server = Server.getInstance();

        // Check if the specified game exists
        if (!server.gameExists(roomName)) {
            player.sendMessage("display Error Game room '" + roomName + "' does not exist.");
            return;
        }

        // Retrieve the game from the server
        Game game = server.getGame(roomName);

        // Check for duplicate nickname
        for(Player p: game.getPlayers()){
            if(p.getNickname().equals(args[2])){
                player.sendMessage("display Error player with this nickname exists");
                return;
            }
        }

        // Try to add the player to the game
        if (game.addPlayer(player)) {
            player.sendMessage("display Success You have joined the game '" + roomName + "'.");
            player.setActiveGame(game);
            player.setNickname(args[2]);

            StringBuilder commandBuilder = new StringBuilder();

            for(int i = 0; i < game.getPlayers().size()-1; i++){
                commandBuilder.append(game.getPlayer(i).getNickname()).append(" ");
            }

            // Update list of players for each player in the game
            game.broadcast("updateList " + player.getNickname());

            // Send information about accepting join request
            // acceptJoin <gameType> <room name> <player Id> <players> <nickname>
            player.sendMessage("acceptJoin " + game.getGameType() + " " + roomName + " " + player.getId() + " " + commandBuilder.toString() + player.getNickname());

        } else {
            player.sendMessage("display Error Game '" + roomName + "' is full.");
        }
    }
}
