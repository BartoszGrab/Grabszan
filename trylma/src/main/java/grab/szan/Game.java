package grab.szan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import grab.szan.boards.Board;
import grab.szan.bots.Bot;
import grab.szan.bots.strategies.NormalBotStrategy;
import grab.szan.gameModes.GameMode;
import grab.szan.gameModes.GameModeHandler;

public class Game {
    //room to nazwa pokoju dla danej rozgrywki
    private String room;
    private int maxPlayers;
    private List<Player> players;
    private GameMode mode;
    private Board board;
    private String gameType;
    private GameState state;

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

    public String getGameType(){
        return gameType;
    }

    /**
     * Zwraca gracza który może wykonać ruch
     * @return gracz który może wykonać ruch
     */
    public Player getCurrentPlayer(){
        return players.get(currentIndex);
    }

    public Player getPlayer(int id){
        return players.get(id);
    }

    /**
     * inicjuje obiekt Game
     * @param room - nazwa pokoju
     * @param maxPlayers - ilość graczy
     * @param mode - tryb gry
     * @param board - plansza
     */
    public Game(String room, int maxPlayers, String gameType){
        players = new ArrayList<>();
        this.room = room;
        this.maxPlayers = maxPlayers;
        this.gameType = gameType;

        mode = GameModeHandler.getGameModeHandler().getGameMode(gameType);
        board = mode.getBoard();
        board.generateBoard();

        random = new Random();

        state = GameState.NOTSTARTED;
        startIndex = 0;
    }

    /**
     * metoda odpowiedzialna za dodawanie gracza do rozgrywki
     * @param player - gracz który ma być dodany
     * @return true jeśli pomyślnie dodano gracza, false w przeciwnym wypadku
     */
    public boolean addPlayer(Player player){
        if(players.size() < maxPlayers){
            players.add(player);
            player.setId(players.size());
            return true;
        }
        return false;
    }

    /**
     * metoda zwracająca listę graczy obecnych w danej rozgrywce
     * @return obiekt typu List<Player> zawierający obecnych graczy
     */
    public List<Player> getPlayers() {
        return players;
    }
    
    /**
     * 
     * @param message
     */
    public void broadcast(String message) {
        synchronized(this){
            for (Player player : players) {
                if(!(player instanceof Bot))
                    player.sendMessage(message);
            }
        }   
    }

    /**
     * metoda do rozpoczynania rozgrywki
     */
    public void startGame(){
        if(state.equals(GameState.STARTED)){
            broadcast("display Error game already started");
            return;
        }

        //sprawdzenie czy mamy wystarczająco graczy
        while(!mode.getAllowedNumOfPlayers().contains(players.size())){
            Bot newBot = new Bot();
            broadcast("updateList " + newBot.getNickname());
            newBot.setStrategy(new NormalBotStrategy());
            newBot.setActiveGame(this);
            addPlayer(newBot);
            new Thread(newBot).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        //rozstawienie pionków
        mode.initializePieces(board, players);
        
        for(int i = 0; i < board.getCols(); i++){
            for(int j = 0; j < board.getRows(); j++){
                Field field = board.getField(j, i);
                if(field == null) continue;

                Player player = field.getPlayer();
                if(player == null) continue;

                broadcast("set " + j + " " + i + " " + player.getId());
            }
        }

        //after setting up the board we need to update the colors of nicknames for each client
        for(Player player : players){
            broadcast("changeColor " + player.getNickname() + " " + player.getId());
        }

        state = GameState.STARTED;

        //wylosowanie gracza który rozpoczyna kolejke
        startIndex = random.nextInt(0, players.size());
        currentIndex = startIndex;
        broadcast("updateTurn " + players.get(currentIndex).getNickname());
    }

    /**
     * metoda do wykonywania ruchu przez gracza który aktualnie może wykonać ruch
     * @param startRow - rząd pozycji startowej
     * @param startCol - kolumna pozycji startowej
     * @param endRow - rząd destynacji
     * @param endCol - kolumna destynacji
     * @return true jeśli ruch jest możliwy do wykonania, false w przeciwnym przypadku
     */
    public boolean moveCurrentPlayer(int startRow, int startCol, int endRow, int endCol){
        //gracz który ma teraz ruch
        Player player = players.get(currentIndex);

        //sprawdzenie czy ruch moze zostac wykonany
        if(!isValidMove(startRow, startCol, endRow, endCol, player)){
            player.sendMessage("display Error invalid move!");
            return false;
        }

        //przeniesienie pionka
        board.getField(endRow, endCol).setPlayer(player);
        board.getField(startRow, startCol).setPlayer(null);

        //zmiana indeksu gracza który ma ruch
        currentIndex++;
        currentIndex %= players.size();

        //sprawdzenie czy gra sie zakończyła
        Player winner = mode.getWinner(board, players);
        if(winner != null){
            broadcast("display Game-finished! Player " + winner.getId() + " won");
        }else{
            broadcast("updateTurn " + players.get(currentIndex).getNickname());

        }

        return true;
    }

    public void nextTurn() {
        currentIndex = (currentIndex + 1) % players.size();
    }
    
    /**
     * metoda do sprawdzania poprawności ruchu
     * @param startRow 
     * @param startCol
     * @param endRow
     * @param endCol
     * @param player
     * @return true jeśli ruch jest poprawny, false w przeciwnym przypadku
     */
    private boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Player player){
        //sprawdzanie czy podane argumenty mieszczą się w zakresie tablicy 
        if (startRow < 0 || startRow >= board.getRows() || 
            startCol  < 0 || startCol >= board.getCols() ||
            endRow < 0 || endRow >= board.getRows() || 
            endCol < 0 || endCol >= board.getCols()){
                player.sendMessage("display Error no field with that position exists");
                return false;
        }

        //sprawdzenie czy istnieje grywalne pole na danej pozycji
        if(board.getField(endRow, endCol) == null || board.getField(startRow, startCol) == null){
            player.sendMessage("display Errror there's no field on given position");
            return false;
        }

        //sprawdzenie czy na polu startowym gracz wykonujący ruch ma pionek
        if(board.getField(startRow, startCol).getPlayer() == null || !board.getField(startRow, startCol).getPlayer().equals(player)){
            player.sendMessage("display Error You don't have a pawn on that position");
            return false;
        }

        //sprawdzenie czy pole destynacji jest puste
        if(board.getField(endRow, endCol).getPlayer() != null){
            player.sendMessage("display Error there is already a pawn at that destination");
            return false;
        }

        //jeśli wszystko powyższe jest spełnione i da się dojsć do pola destynacji to zwracamy prawda
        return board.getReachableFields(board.getField(startRow, startCol)).contains(board.getField(endRow, endCol));
    }
}
