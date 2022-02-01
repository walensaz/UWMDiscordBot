package org.uwmdiscord.core.publishers.events;

import net.dv8tion.jda.api.JDA;

public class BotInitializedEvent {

    private JDA jda;

    public BotInitializedEvent(JDA jda) {
        this.jda = jda;
    }

    public JDA getJda() {
        return jda;
    }
}
