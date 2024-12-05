package grab.szan.commands;

/*
 * interfejs komendy
 */
public interface Command{
    /*
     * metoda execute do wykonywania komendy
     * @param args argumenty dla komendy
     */
    public void execute(String[] args);
}