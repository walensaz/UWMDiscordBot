package org.uwmdiscord.core.publishers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.uwmdiscord.core.Config;
import org.uwmdiscord.core.publishers.events.BotInitializedEvent;
import org.uwmdiscord.core.annotations.UWMSubscribe;
import org.uwmdiscord.core.logging.Logger;
import org.uwmdiscord.core.module.EventBus;
import org.uwmdiscord.core.publishers.events.UWMCommandReceiveEvent;
import org.uwmdiscord.core.publishers.events.UWMMessageReceiveEvent;

public class MessageEventPublisher extends ListenerAdapter {

    @UWMSubscribe
    public void onInit(BotInitializedEvent event) {
        event.getJda().addEventListener(this);
        Logger.info("Added default event listeners.");
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        boolean isCommand = false;
        if (event.isFromGuild()) {
            if (event.getMessage().getContentRaw().startsWith(Config.COMMAND_PREFIX)) {
                UWMCommandReceiveEvent commandEvent = new UWMCommandReceiveEvent(event);
                EventBus.getInstance().publishCommand(commandEvent.getCommand(), commandEvent);
                isCommand = true;
            }
            EventBus.getInstance().publish(UWMMessageReceiveEvent.class, new UWMMessageReceiveEvent(event, isCommand));
        }
    }
}
