package grab.szan.commands;
import grab.szan.utils.Utils;

public class DisplayCommand implements Command{

    @Override
    public void execute(String[] args) {
        if(args.length < 2){
            return;
        }
    
        StringBuilder messageBuilder = new StringBuilder();

        for(int i = 2; i< args.length; i++){
            messageBuilder.append(args[i]).append(" ");
        }

        Utils.showAlert(args[1], messageBuilder.toString());
    }
    
}