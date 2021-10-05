package org.uwmdiscord.core.annotations;

import java.util.Comparator;

/** Decides when a listener should be called, priority goes from HIGHEST -> LOWEST
 * Listener will be called first with HIGHEST and will be called last with LOWEST/FINAL
 * ONLY USE FINAL IF YOU KNOW WHAT YOU'RE DOING!!!
 */
public enum EventPriority implements Comparator<EventPriority> {
    HIGHEST(1),
    HIGH(2),
    NORMAL(3),
    LOW(4),
    LOWEST(5),
    FINAL(8);

    private int rank;

    EventPriority(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }


    @Override
    public int compare(EventPriority o1, EventPriority o2) {
        return o1.getRank() - o2.getRank();
    }
}

