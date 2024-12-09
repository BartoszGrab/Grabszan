package grab.szan.commands;

import grab.szan.Player;

public class MoveCommand implements Command{

    @Override
    public void execute(String[] args, Player player) {
        //sprawdzenie poprawności argumentów
        if(args.length < 5){
            player.sendMessage("Error: too few arguments. Usage: move <start_row> <start_col> <end_row> <end_col>.");
            return;
        }

        //sprawdzenie czy gracz jest częścią istniejącej gry
        if(player.getActiveGame() == null){
            player.sendMessage("you're currently not part of any game!");
            return;
        }

        try{
            //pozycja poczatkowa
            int startRow = Integer.parseInt(args[1]);
            int startCol = Integer.parseInt(args[2]);

            //pozycja końcowa
            int endRow = Integer.parseInt(args[3]);
            int endCol = Integer.parseInt(args[4]);

            //przesuwanie gracza
            player.getActiveGame().move(startRow, startCol, endRow, endCol, player);
            player.sendMessage("player moved succesfully");

        }catch(NumberFormatException e){
            player.sendMessage("arguments should be integers");
            return;
        }
    }
    
}
