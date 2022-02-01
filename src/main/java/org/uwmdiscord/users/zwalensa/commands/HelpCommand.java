package org.uwmdiscord.users.zwalensa.commands;

import org.uwmdiscord.core.annotations.Command;
import org.uwmdiscord.core.annotations.CommandHandler;
import org.uwmdiscord.core.publishers.events.UWMCommandReceiveEvent;

@Command(command = "ping")
public class HelpCommand implements CommandHandler {

    @Override
    public void handle(UWMCommandReceiveEvent event) {
        event.addMessageAction("Pong!");
    }
}
