package grab.szan.commands;

import grab.szan.Player;

public class StartGameCommand implements Command{

    @Override
    public void execute(String[] args, Player player) {
        //TODO w tej wersji kazdy gracz moze wyslac tą komende, chcemy aby tylko osoba która utowrzyła gre mogła ją zacząć
        player.getActiveGame().startGame();
    }
    
}
