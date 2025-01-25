package grab.szan.bots;

import grab.szan.Player;
import grab.szan.bots.strategies.BotStrategy;
import grab.szan.commands.CommandHandler;

/**class representing Bot player */
public class Bot extends Player {
    private BotStrategy strategy;

    public Bot(CommandHandler commandHandler) {
        super(commandHandler);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * sets strategy for making moves
     * @param strategy - interface with method responsible for making moves
     */
    public void setStrategy(BotStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void run(){
        while (true) {
            try {
                if(getActiveGame().getCurrentPlayer().equals(this)) {
                    strategy.makeMove(this);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }
}
