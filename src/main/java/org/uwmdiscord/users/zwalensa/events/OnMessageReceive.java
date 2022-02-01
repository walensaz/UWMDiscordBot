package org.uwmdiscord.users.zwalensa.events;

import net.dv8tion.jda.api.requests.RestAction;
import org.uwmdiscord.core.annotations.EventPriority;
import org.uwmdiscord.core.annotations.UWMSubscribe;
import org.uwmdiscord.core.publishers.events.UWMCommandReceiveEvent;
import org.uwmdiscord.core.publishers.events.UWMMessageReceiveEvent;

public class OnMessageReceive {

    @UWMSubscribe
    public void onEvent(UWMMessageReceiveEvent e) {
        if (e.getAuthor().isBot()) return;
        e.addMessageAction(e.getChannel().sendMessage("Received this normal message"));
    }

    @UWMSubscribe(priority = EventPriority.FINAL)
    public void onMessage(UWMMessageReceiveEvent e) {
        if (e.getAuthor().isBot()) return;
        e.getMessageActions().forEach(RestAction::queue);
    }

    @UWMSubscribe
    public void onCommand(UWMCommandReceiveEvent e) {

    }
}
