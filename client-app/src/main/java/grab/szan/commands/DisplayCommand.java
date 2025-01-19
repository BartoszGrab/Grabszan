package grab.szan.commands;

import grab.szan.utils.Utils;

/**
 * A command that displays a message (via an alert) on the client side.
 * <p>
 * Usage: display &lt;title&gt; &lt;message...&gt;
 */
public class DisplayCommand implements Command {

    /**
     * Executes the display command to show an alert with the given title and message.
     *
     * @param args the command arguments; args[1] is the title, and args[2..] is the message
     */
    @Override
    public void execute(String[] args) {
        if(args.length < 2){
            return;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for(int i = 2; i < args.length; i++){
            messageBuilder.append(args[i]).append(" ");
        }

        Utils.showAlert(args[1], messageBuilder.toString());
    }
}
