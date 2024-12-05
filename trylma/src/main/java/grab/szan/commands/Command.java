package grab.szan.commands;

import grab.szan.Player;

/*
 * interfejs komendy
 */
public interface Command{
    /*
     * metoda execute do wykonywania komendy
     * @param args argumenty dla komendy
     */
    public void execute(String[] args, Player player);
}