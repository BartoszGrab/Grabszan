package grab.szan.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import grab.szan.Player;
import grab.szan.database.entities.MoveEntity;
import grab.szan.spring.services.MoveService;

@Component
public class ReplayCommand implements Command {

    @Autowired
    private MoveService moveService;
    
    @Override
    public void execute(String[] args, Player player) {
        if (args.length < 2) {
            player.sendMessage("Error too few arguments.");
            return;
        }

        String room = args[1];
        List<MoveEntity> moves = moveService.getMoves(room);

        for (MoveEntity move : moves) {
            try {
                player.sendMessage("set " + move.getStartRow() + " " + move.getStartCol() + " " + 6);
                player.sendMessage("set " + move.getEndRow() + " " + move.getEndCol() + " " + player.getId());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();;
            }
        }
    }

    @Override
    public String getName() {
        return "replay";
    }

}
