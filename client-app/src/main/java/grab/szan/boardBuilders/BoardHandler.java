package grab.szan.boardBuilders;

import java.util.HashMap;

/**
 * Handles mapping between board names and their corresponding Board objects.
 */
public class BoardHandler {
    private static BoardHandler boardHandler;
    private HashMap<String, Board> boardMap;

    /**
     * Private constructor to initialize the board mapping.
     */
    private BoardHandler() {
        boardMap = new HashMap<>();
        boardMap.put("Classic", new ClassicBoard());
        boardMap.put("Ying-Yang", new YingYangBoard());
    }

    /**
     * Retrieves the singleton instance of BoardHandler.
     *
     * @return the singleton instance of BoardHandler.
     */
    public static BoardHandler getBoardHandler() {
        BoardHandler localHandler = boardHandler;

        // Double-checked locking to ensure thread-safe singleton instantiation.
        if (localHandler == null) {
            synchronized (BoardHandler.class) {
                localHandler = boardHandler;
                if (localHandler == null) {
                    boardHandler = localHandler = new BoardHandler();
                }
            }
        }
        return boardHandler;
    }

    /**
     * Maps a board name to a Board object.
     *
     * @param boardName the name of the board.
     * @param board     the Board object to map.
     */
    public void addBoard(String boardName, Board board) {
        boardMap.put(boardName, board);
    }

    /**
     * Retrieves a Board object associated with a given board name.
     *
     * @param boardName the name of the board.
     * @return the corresponding Board object.
     * @throws IllegalArgumentException if the board name does not exist in the mapping.
     */
    public Board getBoard(String boardName) {
        if (!boardMap.containsKey(boardName)) {
            throw new IllegalArgumentException("Invalid board name: " + boardName);
        }
        return boardMap.get(boardName);
    }
}
