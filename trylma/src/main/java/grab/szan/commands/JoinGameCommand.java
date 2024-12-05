package grab.szan.commands;

import grab.szan.Player;

public class JoinGameCommand implements Command{

    @Override
    public void execute(String[] args, Player player) {
        //args[0] zawsze bedzie typem komendy czyli w tym przypadku
        //args[0] = "join", dopiero kolejne argumenty to parametry
        // TODO funkcja powinna przyjmowac jako argumenty nazwe gry do ktorej chcemy dolaczyc
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
