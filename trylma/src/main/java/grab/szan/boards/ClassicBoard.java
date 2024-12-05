package grab.szan.boards;

import grab.szan.Field;

/*
 * klasa przedstawiajaca klasyczną plansze do gry w trylme
 */
public class ClassicBoard extends Board{
    public ClassicBoard(){
        super(17, 25);
    }

    @Override
    public void generateBoard(){
        //generowanie gornego trojkata
        for(int i = 0; i < 4; i++){
            for(int j = 12-i; j <= 12+i; j += 2){
                fields[i][j] = new Field();
            }
        }

        //generowanie srodkowej czesci 1.
        for(int i = 4; i <= 8; i++){
            for(int j = i-4; j < 29-i; j += 2){
                fields[i][j] = new Field();
            }
        }

        //generowanie srodkowej czesci 2.
        for(int i = 9; i <= 12; i++){
            for(int j = 12-i; j<= 12+i; j += 2){
                fields[i][j] = new Field();
            }
        }

        //generowanie dolnego trojkata
        for(int i = 13; i <= 17; i++){
            for(int j = i-4; j <= 28-i; j += 2){
                fields[i][j] = new Field();
            }
        }
    }
}
