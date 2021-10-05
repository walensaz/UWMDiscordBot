package org.uwmdiscord.core.publishers.events;

import net.dv8tion.jda.api.events.GenericEvent;

public interface UWMJDAEvent {
    GenericEvent getRawEvent();
}
