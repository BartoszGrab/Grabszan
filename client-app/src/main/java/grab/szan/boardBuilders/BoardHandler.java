package grab.szan.boardBuilders;

import java.util.HashMap;

public class BoardHandler {
     private static BoardHandler boardHandler;

    private HashMap<String, BoardBuilder> boardMap;

    private BoardHandler(){
        boardMap = new HashMap<>();
        boardMap.put("Classic", new ClassicBoardBuilder());
        boardMap.put("Ying-Yang", new ClassicBoardBuilder());
    }

    /**
     * Metoda pozwalająca na pozyskanie instancji obiektu BoardHandler
     * 
     * @return instancja BoardHandler
     */
    public static BoardHandler getBoardHandler(){
        BoardHandler localHandler = boardHandler;

        //double-checked locking
        if(localHandler == null){
            synchronized(BoardHandler.class){
                localHandler = boardHandler;
                if(localHandler == null){
                    boardHandler = localHandler = new BoardHandler();
                }
            }
        }
        return boardHandler;
    }

   /**
    * Metoda do mapowania polecenia w postaci String z 
    * obiektem typu Board 
    * @param BoardLine - polecenie wpisywane w terminalu
    * @param executable - obiekt implementujacy Board z metodą execute
    */
    public void addBoard(String BoardLine, BoardBuilder Board){
        boardMap.put(BoardLine, Board);
    }


    /**
     * Metoda do pozyskiwania obiektu typu Board odpowiadającemu wprowadzonej komendzie
     * @param BoardLine - wprowadzona komenda
     * @return obiekt typu Board jeśli wprowadzona komenda istnieje
     * @throws IllegalArgumentException jeślli komenda nie istnieje
     */
    public BoardBuilder getBoard(String BoardLine){
        if(!boardMap.containsKey(BoardLine)){
            throw new IllegalArgumentException("invalid Board line");
        }
        return boardMap.get(BoardLine);
    }
}
