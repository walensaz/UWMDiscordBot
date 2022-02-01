package org.uwmdiscord.core.annotations;

import org.uwmdiscord.core.publishers.events.UWMCommandReceiveEvent;

public interface CommandHandler {
    void handle(UWMCommandReceiveEvent event);
}
