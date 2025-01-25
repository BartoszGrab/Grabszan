package grab.szan.commands;

import org.springframework.stereotype.Component;

import grab.szan.Player;

@Component
public class SetNickCommand  implements Command{

    @Override
    public void execute(String[] args, Player player) {
        if(args.length < 2) {
            player.sendMessage("Error: too few arguments. Usage: nick <nickname>");
            return;
        }

        player.setNickname(args[1]);
    }
    
}
