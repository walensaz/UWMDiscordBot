package org.uwmdiscord.core.publishers.events;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper event to more easily get objects out of a Message Received Event
 * Please do not queue/flush the message channel, that will be done at the end
 */
public class UWMMessageReceiveEvent implements UWMJDAEvent {

    private MessageReceivedEvent rawEvent;
    private ChannelType channelType;
    private MessageChannel messageChannel;
    private User author;
    private List<MessageAction> messageActions;


    public UWMMessageReceiveEvent(MessageReceivedEvent rawEvent) {
        this.rawEvent = rawEvent;
        this.channelType = rawEvent.getChannelType();
        this.messageChannel = rawEvent.getChannel();
        this.messageActions = new ArrayList<>();
        this.author = rawEvent.getAuthor();
    }

    public void addMessageAction(MessageAction action) {
        messageActions.add(action);
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public MessageChannel getChannel() {
        return messageChannel;
    }

    public List<MessageAction> getMessageActions() {
        return messageActions;
    }

    public User getAuthor() {
        return author;
    }

    @Override
    public MessageReceivedEvent getRawEvent() {
        return rawEvent;
    }
}
