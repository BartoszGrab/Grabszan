package grab.szan.commands;

import grab.szan.controller.Controller;

public interface Command {
    public void execute(String[] args, Controller controller);
}
