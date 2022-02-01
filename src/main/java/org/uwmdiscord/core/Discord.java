package org.uwmdiscord.core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.uwmdiscord.core.publishers.events.BotInitializedEvent;
import org.uwmdiscord.core.logging.Logger;
import org.uwmdiscord.core.module.EventBus;

import javax.security.auth.login.LoginException;

public class Discord {

    public static JDA jda;

    public static void main(String[] args) {
        try {
            init();
        } catch(InterruptedException e) {System.exit(0);}
    }

    public static void init() throws InterruptedException {
        try {
            Discord.jda = JDABuilder.createDefault(Config.TOKEN).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        if (jda == null) {
            Logger.error("Was not able to initialize the bot.");
            System.exit(0);
        }
        jda.awaitReady();
        EventBus.getInstance();
        EventBus.getInstance().publish(BotInitializedEvent.class, new BotInitializedEvent(jda));
    }


}
