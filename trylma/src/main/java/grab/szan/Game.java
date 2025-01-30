package grab.szan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import grab.szan.boards.Board;
import grab.szan.bots.Bot;
import grab.szan.bots.BotNameGenerator;
import grab.szan.bots.strategies.BotStrategy;
import grab.szan.commands.CommandHandler;
import grab.szan.gameModes.GameMode;
import grab.szan.gameModes.GameModeHandler;

/**
 * Represents a game session, managing players, the board, and game state.
 */
public class Game {
    private Long gameEntityId; // ID corresponding to the record in the database (GameEntity.id)
    private String dbStatus;   // Additional status (e.g., "NOTSTARTED"/"STARTED"/"ENDED")

    private String room;       // Name of the game room.
    private int maxPlayers;
    private List<Player> players;
    private GameMode mode;
    private Board board;
    private String gameType;
    private GameState state;

    private CommandHandler commandHandler;
    private BotStrategy botStrategy;

    private int startIndex;    // Index of the player who starts the turn.
    private Random random;
    private int currentIndex;  // Index of the current player.

    /**
     * Initializes a game session.
     *
     * @param room       the name of the game room.
     * @param maxPlayers the maximum number of players.
     * @param gameType   the type of the game.
     */
    public Game(String room, int maxPlayers, String gameType, BotStrategy strategy) {
        this.room = room;
        this.maxPlayers = maxPlayers;
        this.gameType = gameType;
        this.botStrategy = strategy;

        players = new ArrayList<>();
        state = GameState.NOTSTARTED;
        dbStatus = "NOTSTARTED";

        mode = GameModeHandler.getGameModeHandler().getGameMode(gameType);
        board = mode.getBoard();
        board.generateBoard();

        random = new Random();
        startIndex = 0;
    }

    public Long getGameEntityId() {
        return gameEntityId;
    }

    public void setGameEntityId(Long gameEntityId) {
        this.gameEntityId = gameEntityId;
    }

    public String getDbStatus() {
        return dbStatus;
    }

    public void setDbStatus(String dbStatus) {
        this.dbStatus = dbStatus;
    }

    public String getRoom() {
        return room;
    }

    public Board getBoard() {
        return board;
    }

    public String getGameType() {
        return gameType;
    }

    public GameState getState() {
        return state;
    }

    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }

    public boolean nicknameExists(String nickname) {
        for(Player player : players) {
            if(player.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean addPlayer(Player player) {
        if (players.size() < maxPlayers) {
            players.add(player);
            player.setId(players.size());
            return true;
        }
        return false;
    }

    public void broadcast(String message) {
        synchronized (this) {
            for (Player player : players) {
                if (!(player instanceof Bot)) {
                    player.sendMessage(message);
                }
            }
        }
    }

    /**
     * Starts the game session.
     */
    public void startGame() {
        if (state.equals(GameState.STARTED)) {
            broadcast("display Error game already started");
            return;
        }

        // Ensure there are enough players.
        while (players.size() < maxPlayers) {
            String nickname = BotNameGenerator.getRandomName();
            if(nicknameExists(nickname)) continue;

            Bot newBot = new Bot(commandHandler);
            newBot.setNickname(nickname);

            broadcast("updateList " + newBot.getNickname());

            newBot.setStrategy(botStrategy);
            newBot.setActiveGame(this);

            addPlayer(newBot);

            new Thread(newBot).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Initialize pieces on the board.
        mode.initializePieces(board, players);

        for (int i = 0; i < board.getCols(); i++) {
            for (int j = 0; j < board.getRows(); j++) {
                Field field = board.getField(j, i);
                if (field == null) continue;
                Player player = field.getPlayer();
                if (player == null) continue;
                broadcast("set " + j + " " + i + " " + player.getId());
            }
        }

        // Update nickname colors for all players.
        for (Player player : players) {
            broadcast("changeColor " + player.getNickname() + " " + player.getId());
        }

        state = GameState.STARTED;
        dbStatus = "STARTED";

        // Randomly select a player to start the turn.
        startIndex = random.nextInt(players.size());
        currentIndex = startIndex;
        broadcast("updateTurn " + players.get(currentIndex).getNickname());
    }

    /**
     * Moves the current player's piece from the start position to the end position.
     * 
     * @param startRow the row of the start position.
     * @param startCol the column of the start position.
     * @param endRow   the row of the end position.
     * @param endCol   the column of the end position.
     * @return true if the move is valid, false otherwise.
     */
    public boolean moveCurrentPlayer(int startRow, int startCol, int endRow, int endCol) {
        // Additional verification: if the game is in the ENDED state, do not make a move
        if (state.equals(GameState.ENDED)) {
            getCurrentPlayer().sendMessage("display Error the game is already finished!");
            return false;
        }

        Player player = players.get(currentIndex);

        if (!isValidMove(startRow, startCol, endRow, endCol, player)) {
            player.sendMessage("display Error invalid move!");
            return false;
        }

        board.getField(endRow, endCol).setPlayer(player);
        board.getField(startRow, startCol).setPlayer(null);

        currentIndex = (currentIndex + 1) % players.size();

        Player winner = mode.getWinner(board, players);
        if (winner != null) {
            broadcast("display Game-finished! Player " + winner.getNickname() + " won");
            state = GameState.ENDED;
            dbStatus = "ENDED";
        } else {
            broadcast("updateTurn " + players.get(currentIndex).getNickname());
        }

        return true;
    }

    /**
     * Advances the turn to the next player.
     */
    public void nextTurn() {
        if (state.equals(GameState.ENDED)) {
            return;
        }
        currentIndex = (currentIndex + 1) % players.size();
    }

    /**
     * Validates whether a move is allowed.
     */
    private boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Player player) {
        if (startRow < 0 || startRow >= board.getRows() ||
            startCol < 0 || startCol >= board.getCols() ||
            endRow < 0   || endRow   >= board.getRows() ||
            endCol < 0   || endCol   >= board.getCols()) {
            player.sendMessage("display Error no field with that position exists");
            return false;
        }

        if (board.getField(endRow, endCol) == null || board.getField(startRow, startCol) == null) {
            player.sendMessage("display Error there's no field on the given position");
            return false;
        }

        if (board.getField(startRow, startCol).getPlayer() == null
            || !board.getField(startRow, startCol).getPlayer().equals(player)) {
            player.sendMessage("display Error You don't have a pawn on that position");
            return false;
        }

        if (board.getField(endRow, endCol).getPlayer() != null) {
            player.sendMessage("display Error there is already a pawn at that destination");
            return false;
        }

        // Check if the end position is among the reachable fields (normal move or jump)
        return board.getReachableFields(board.getField(startRow, startCol))
                    .contains(board.getField(endRow, endCol));
    }
}
