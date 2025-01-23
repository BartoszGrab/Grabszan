package grab.szan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import grab.szan.boards.Board;
import grab.szan.bots.Bot;
import grab.szan.bots.strategies.NormalBotStrategy;
import grab.szan.gameModes.GameMode;
import grab.szan.gameModes.GameModeHandler;

/**
 * Represents a game session, managing players, the board, and game state.
 */
public class Game {
    private String room; // Name of the game room.
    private int maxPlayers; // Maximum number of players.
    private List<Player> players; // List of players in the game.
    private GameMode mode; // The game mode.
    private Board board; // The game board.
    private String gameType; // Type of the game.
    private GameState state; // Current state of the game.

    private int startIndex; // Index of the player who starts the turn.
    private Random random;
    private int currentIndex; // Index of the current player.

    /**
     * Gets the name of the game room.
     * 
     * @return the room name.
     */
    public String getRoom() {
        return room;
    }

    /**
     * Gets the game board.
     * 
     * @return the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the type of the game.
     * 
     * @return the game type.
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Gets the current player who can make a move.
     * 
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    /**
     * Gets a player by their ID.
     * 
     * @param id the ID of the player.
     * @return the player.
     */
    public Player getPlayer(int id) {
        return players.get(id);
    }

    /**
     * Initializes a game session.
     * 
     * @param room       the name of the game room.
     * @param maxPlayers the maximum number of players.
     * @param gameType   the type of the game.
     */
    public Game(String room, int maxPlayers, String gameType) {
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
     * Adds a player to the game.
     * 
     * @param player the player to add.
     * @return true if the player was added, false otherwise.
     */
    public boolean addPlayer(Player player) {
        if (players.size() < maxPlayers) {
            players.add(player);
            player.setId(players.size());
            return true;
        }
        return false;
    }

    /**
     * Gets the list of players in the game.
     * 
     * @return a list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Broadcasts a message to all players.
     * 
     * @param message the message to broadcast.
     */
    public void broadcast(String message) {
        synchronized (this) {
            for (Player player : players) {
                if (!(player instanceof Bot))
                    player.sendMessage(message);
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
            Bot newBot = new Bot();
            broadcast("updateList " + newBot.getNickname());

            newBot.setStrategy(new NormalBotStrategy());
            newBot.setActiveGame(this);

            if(!addPlayer(newBot)) continue;

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
                if (field == null)
                    continue;

                Player player = field.getPlayer();
                if (player == null)
                    continue;

                broadcast("set " + j + " " + i + " " + player.getId());
            }
        }

        // Update nickname colors for all players.
        for (Player player : players) {
            broadcast("changeColor " + player.getNickname() + " " + player.getId());
        }

        state = GameState.STARTED;

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
            broadcast("display Game-finished! Player " + winner.getId() + " won");
        } else {
            broadcast("updateTurn " + players.get(currentIndex).getNickname());
        }

        return true;
    }

    /**
     * Advances the turn to the next player.
     */
    public void nextTurn() {
        currentIndex = (currentIndex + 1) % players.size();
    }

    /**
     * Validates whether a move is allowed.
     * 
     * @param startRow the row of the start position.
     * @param startCol the column of the start position.
     * @param endRow   the row of the end position.
     * @param endCol   the column of the end position.
     * @param player   the player making the move.
     * @return true if the move is valid, false otherwise.
     */
    private boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Player player) {
        if (startRow < 0 || startRow >= board.getRows() || startCol < 0 || startCol >= board.getCols() || endRow < 0
                || endRow >= board.getRows() || endCol < 0 || endCol >= board.getCols()) {
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

        return board.getReachableFields(board.getField(startRow, startCol)).contains(board.getField(endRow, endCol));
    }
}
