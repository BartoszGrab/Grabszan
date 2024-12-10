package grab.szan.commands;

import grab.szan.Player;

/*
 * interfejs komendy
 */
public interface Command{
  /**
   * Metoda wykonująca komende
   * @param args - lista parametrów potrzebnych do wykonania komendy
   * @param player - klient który wysłał komende
   */
    public void execute(String[] args, Player player);
}