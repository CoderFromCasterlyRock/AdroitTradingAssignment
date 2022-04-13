package com.adroit.trading.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class NamedThreadFactory implements ThreadFactory {

    private final String name;

    private static final AtomicInteger COUNT = new AtomicInteger();

    public NamedThreadFactory( String name ) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.setName("Thread-" + COUNT.incrementAndGet() + "-" + name);

        return thread;
    }

}
