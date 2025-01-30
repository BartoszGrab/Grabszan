package grab.szan.commands;

import grab.szan.GameState;
import grab.szan.Player;
import grab.szan.spring.services.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command to handle a player's move.
 */
@Component
public class MoveCommand implements Command {

    @Autowired
    private MoveService moveService;

    /**
     * Executes the move command.
     * Usage: move <start_row> <start_col> <end_row> <end_col>
     *
     * @param args   the command arguments
     * @param player the player issuing the command
     */
    @Override
    public void execute(String[] args, Player player) {
        // Check for sufficient arguments
        if(args.length < 5){
            player.sendMessage("display Error too few arguments. Usage: move <start_row> <start_col> <end_row> <end_col>.");
            return;
        }

        // Check if the player is part of a game
        if(player.getActiveGame() == null){
            player.sendMessage("display Error you are currently not part of any game!");
            return;
        }

        if(!player.getActiveGame().getState().equals(GameState.STARTED)) {
            player.sendMessage("Game has already ended!");
            return;
        }

        // Check if it's the player's turn
        if(!player.getActiveGame().getCurrentPlayer().equals(player)){
            player.sendMessage("display Error it's not your turn!");
            return;
        }

        try {
            // Parse start and end coordinates
            int startRow = Integer.parseInt(args[1]);
            int startCol = Integer.parseInt(args[2]);
            int endRow = Integer.parseInt(args[3]);
            int endCol = Integer.parseInt(args[4]);

            // Attempt to move the piece
            if(player.getActiveGame().moveCurrentPlayer(startRow, startCol, endRow, endCol)){
                // Record the move in the database
                moveService.recordMove(
                    player.getActiveGame().getRoom(),
                    player.getId(),
                    startRow,
                    startCol,
                    endRow,
                    endCol
                );

                // Inform all players about the move
                player.getActiveGame().broadcast("set " + startRow + " " + startCol + " " + 6);
                player.getActiveGame().broadcast("set " + endRow + " " + endCol + " " + player.getId());
            }

        } catch(NumberFormatException e){
            player.sendMessage("arguments should be integers");
        } catch(IllegalArgumentException e){
            player.sendMessage("display " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "move";
    }
}
