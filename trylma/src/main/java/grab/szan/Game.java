package grab.szan;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import grab.szan.boards.Board;
import grab.szan.gameModes.GameMode;

public class Game {
    /*room to nazwa dla danej rozgrywki */
    private String room;
    private int maxPlayers;
    private List<Player> players;
    private GameMode mode;
    private Board board;

    //indeks gracza ktory rozpoczyna kolejke
    private int startIndex;
    private Random random;

    //indeks gracza ktory aktualnie ma ruch
    private int currentIndex;

    public String getRoom(){
        return room;
    }

    public Board getBoard(){
        return board;
    }

    /*
     * zwraca gracza który może wykonać ruch
     */
    public Player getCurrentPlayer(){
        return players.get(currentIndex);
    }

    public Game(String room, int maxPlayers, GameMode mode, Board board){
        players = new ArrayList<>();
        this.room = room;
        this.maxPlayers = maxPlayers;
        this.mode = mode;
        this.board = board;

        random = new Random();
    }

    public boolean addPlayer(Player player){
        if(players.size() < maxPlayers){
            players.add(player);
            player.setId(players.size());
            player.sendMessage("your id is " + player.getId());
            return true;
        }
        return false;
    }

    public List<Player> getPlayers() {
        return players;
    }
    
    public void broadcast(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    public void startGame(){
        //sprawdzenie czy mamy wystarczająco graczy
        if(players.size()<maxPlayers){
            broadcast("not enough players to start game");
        }

        //rozstawienie pionków
        mode.initializePieces(board, players);

        //wylosowanie gracza który rozpoczyna kolejke
        startIndex = random.nextInt(0, maxPlayers);
        currentIndex = startIndex;
        broadcast("it's player " + players.get(currentIndex).getId() + "'s turn");
        broadcast("current board state:\n" + board.displayBoard());
    }

    public boolean moveCurrentPlayer(int startRow, int startCol, int endRow, int endCol){
        //gracz który ma teraz ruch
        Player player = players.get(currentIndex);

        //sprawdzenie czy ruch moze zostac wykonany
        if(!isValidMove(startRow, startCol, endRow, endCol, player)){
            player.sendMessage("invalid move!");
            return false;
        }

        //przeniesienie pionka
        board.getField(endRow, endCol).setPlayer(player);
        board.getField(startRow, startCol).setPlayer(null);

        //zmiana indeksu gracza który ma ruch
        currentIndex++;
        currentIndex %= maxPlayers;

        //sprawdzenie czy gra sie zakończyła
        Player winner = mode.getWinner(board, players);
        if(winner != null){
            broadcast("Game finished! Player " + winner.getId() + " won");
        }else{
            broadcast("Player " +  players.get(currentIndex).getId() + "turn");
        }

        return true;
    }

    private boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Player player){
        //sprawdzanie czy podane argumenty mieszczą się w zakresie tablicy 
        if (startRow < 0 || startRow >= board.getRows() || 
            startCol  < 0 || startCol >= board.getCols() ||
            endRow < 0 || endRow >= board.getRows() || 
            endCol < 0 || endCol >= board.getCols()){
                player.sendMessage("no field with that position exists");
                return false;
        }

        //sprawdzenie czy istnieje grywalne pole na danej pozycji
        if(board.getField(endRow, endCol) == null || board.getField(startRow, startCol) == null){
            player.sendMessage("there's no field on given position");
            return false;
        }

        //sprawdzenie czy na polu startowym gracz wykonujący ruch ma pionek
        if(board.getField(startRow, startCol).getPlayer() == null || !board.getField(startRow, startCol).getPlayer().equals(player)){
            player.sendMessage("You don't have a pawn on that position");
            return false;
        }

        //sprawdzenie czy pole destynacji jest puste
        if(board.getField(endRow, endCol).getPlayer() != null){
            player.sendMessage("there is already a pawn at that destination");
            return false;
        }

        //jeśli wszystko powyższe jest spełnione i da się dojsć do pola destynacji to zwracamy prawda
        return isReachable(startRow, startCol, endRow, endCol);
    }
    
    private boolean isReachable(int startRow, int startCol, int endRow, int endCol){
        //najpierw musimy osobno sprawdzic wszystkie miejsca wokol pozycji startowej
        for(int[] nextPos: board.getAvailableDirections()){
            int newRow = startRow + nextPos[0], newCol = startCol + nextPos[1];
            if(newRow >= board.getRows() || newCol >= board.getCols() || newRow < 0 || newCol < 0) continue;

            //jesli jakies miejsce wokol pozycji startowej jest pozycja koncową to zwracamy true
            if(newRow == endRow && newCol == endCol){
                return true;
            }
        }

        Deque<int[]> deque = new ArrayDeque<>();
        deque.offer(new int[]{startRow, startCol});

        //teraz zajmujemy sie przypadkami kiedy 
        while(!deque.isEmpty()){
            int[] curr = deque.poll();
            int x = curr[0], y = curr[1];
            if(x == endRow && y == endCol) return true;
            
            for(int[] nextPos: board.getAvailableDirections()){
                //przesuwamy sie o 2 miejsca w danym kierunku (stad *2)
                int newRow = x + nextPos[0]*2, newCol = y + nextPos[1]*2;
                if(newRow >= board.getRows() || newCol >= board.getCols() || newRow < 0 || newCol < 0) continue;
                if(board.getField(newRow, newCol) == null) continue;

                //sprawdzamy czy w danym kierunku kolejne miejsce jest zajete i dwa miejsca dalej są puste
                if(board.getField(x+nextPos[0], y+nextPos[1]).getPlayer() != null && board.getField(newRow, newCol).getPlayer() == null){
                    deque.offer(new int[]{newRow, newCol});
                }
            }
        }

        return false;
    }
}
