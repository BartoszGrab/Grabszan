package grab.szan.commands;

import org.springframework.stereotype.Component;

import grab.szan.Player;

@Component
public class StartGameCommand implements Command{

    @Override
    public void execute(String[] args, Player player) {
        //TODO w tej wersji kazdy gracz moze wyslac tą komende, chcemy aby tylko osoba która utowrzyła gre mogła ją zacząć
        player.getActiveGame().startGame();
    }

    @Override
    public String getName() {
        return "start";
    }
    
}
