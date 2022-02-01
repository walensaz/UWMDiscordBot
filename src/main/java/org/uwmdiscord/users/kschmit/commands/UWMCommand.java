package org.uwmdiscord.users.kschmit.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.uwmdiscord.core.annotations.Command;
import org.uwmdiscord.core.annotations.CommandHandler;
import org.uwmdiscord.core.publishers.events.UWMCommandReceiveEvent;
import org.uwmdiscord.utils.StringUtils;

@Command(command = "uwm")
public class UWMCommand implements CommandHandler {

    @Override
    public void handle(UWMCommandReceiveEvent event) {
        String description = StringUtils.multiLineString(
                "Paws: https://paws.uwm.edu/signin.html",
                "Canvas: https://uwm.edu/canvas/home/"
                );
        MessageEmbed messageAction = new EmbedBuilder()
                .setTitle("UWM")
                .setDescription(description)
                .build();
        event.addEmbed(messageAction);
    }
}
