package org.uwmdiscord.zwalensa.events;

import net.dv8tion.jda.api.requests.RestAction;
import org.uwmdiscord.core.annotations.EventPriority;
import org.uwmdiscord.core.annotations.UWMListener;
import org.uwmdiscord.core.publishers.events.UWMMessageReceiveEvent;

public class OnMessageReceive {

    @UWMListener
    public void onEvent(UWMMessageReceiveEvent e) {
        if (e.getAuthor().isBot()) return;
        e.addMessageAction(e.getChannel().sendMessage("Received this normal message"));
    }

    @UWMListener(priority = EventPriority.FINAL)
    public void onMessage(UWMMessageReceiveEvent e) {
        if (e.getAuthor().isBot()) return;
        e.getMessageActions().forEach(RestAction::queue);
    }

}
