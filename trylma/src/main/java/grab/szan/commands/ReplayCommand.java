package grab.szan.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import grab.szan.Player;
import grab.szan.database.entities.MoveEntity;
import grab.szan.spring.services.GameService;
import grab.szan.spring.services.MoveService;

@Component
public class ReplayCommand implements Command {

    @Autowired
    private MoveService moveService;

    @Autowired
    private GameService gameService;
    
    @Override
    public void execute(String[] args, Player player) {
        if (args.length < 2) {
            player.sendMessage("display Error too few arguments.");
            return;
        }

        String room = args[1];
        if (!gameService.gameExistsInDB(room)) {
            player.sendMessage("display Error game not found.");
            return;
        }

        player.sendMessage("acceptReplay " + gameService.getGameType(room) + " " + gameService.getMaximumPlayers(room));
        List<MoveEntity> moves = moveService.getMoves(room);

        for (MoveEntity move : moves) {
            try {
                Thread.sleep(500);
                player.sendMessage("set " + move.getStartRow() + " " + move.getStartCol() + " " + 6);
                player.sendMessage("set " + move.getEndRow() + " " + move.getEndCol() + " " + Long.valueOf(move.getPlayerId()));
            } catch (InterruptedException e) {
                e.printStackTrace();;
            }
        }
        player.sendMessage("display Finish Game ended");
    }

    @Override
    public String getName() {
        return "replay";
    }

}
