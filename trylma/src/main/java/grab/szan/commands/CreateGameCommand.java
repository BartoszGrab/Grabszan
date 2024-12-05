package grab.szan.commands;

/*
 * komenda do tworzenia nowej rozgrywki
 */
public class CreateGameCommand implements Command{

    @Override
    public void execute(String[] args) { 
        //args[0] zawsze bedzie typem komendy czyli w tym przypadku
        //args[0] = "create", dopiero kolejne argumenty to parametry
        // TODO funkcja powinna przyjmowac jako argumenty nazwe rozgrywki, oraz liczbe graczy
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
