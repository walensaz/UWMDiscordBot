package org.uwmdiscord.core.publishers.events;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UWMCommandReceiveEvent extends UWMMessageReceiveEvent implements UWMJDAEvent {

    private String command;
    private List<String> args;

    public UWMCommandReceiveEvent(MessageReceivedEvent rawEvent) {
        super(rawEvent, true);
        List<String> content = new ArrayList<>(Arrays.asList(rawEvent.getMessage().getContentRaw().substring(1).split(" ")));
        this.command = content.get(0);
        content.remove(0);
        this.args = content;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArgs() {
        return args;
    }
}
