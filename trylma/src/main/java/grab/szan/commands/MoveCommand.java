package grab.szan.commands;

import grab.szan.Player;

public class MoveCommand implements Command{

    @Override
    public void execute(String[] args, Player player) {
        //sprawdzenie poprawności argumentów
        if(args.length < 5){
            player.sendMessage("display Error too few arguments. Usage: move <start_row> <start_col> <end_row> <end_col>.");
            return;
        }

        //sprawdzenie czy gracz jest częścią istniejącej gry
        if(player.getActiveGame() == null){
            player.sendMessage("display Error you are currently not part of any game!");
            return;
        }

        //sprawdzenie czy gracz moze wykonac ruch
        if(!player.getActiveGame().getCurrentPlayer().equals(player)){
            player.sendMessage("display Error it's not your turn!");
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
            if(player.getActiveGame().moveCurrentPlayer(startRow, startCol, endRow, endCol)){
                player.getActiveGame().broadcast("set " + startRow + " " + startCol + " " + 6);
                player.getActiveGame().broadcast("set " + endRow + " " + endCol + " " + player.getId());
                //player.getActiveGame().broadcast("current board state:\n" + player.getActiveGame().getBoard().displayBoard());
            }

        }catch(NumberFormatException e){
            player.sendMessage("arguments should be integers");
            return;
        }
    }
    
}
