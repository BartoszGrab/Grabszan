package grab.szan.commands;

import grab.szan.Player;

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
