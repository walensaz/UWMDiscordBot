package org.uwmdiscord.core;

import java.util.concurrent.*;

public class Scheduler {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(Config.THREAD_POOL_SIZE);

    public static ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    public static ScheduledFuture<?> schedule(Runnable runnable) {
        return scheduler.schedule(runnable, 100L, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> schedule(Callable<?> func) {
        return scheduler.schedule(func, 100L, TimeUnit.MILLISECONDS);
    }

}
