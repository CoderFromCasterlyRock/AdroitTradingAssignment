package com.adroit.trading.persistence;

import java.util.concurrent.atomic.AtomicInteger;


public final class UrlEntry {

    private final String longUrl;
    private final AtomicInteger count;

    private UrlEntry( String longUrl ){
        this.longUrl = longUrl;
        this.count   = new AtomicInteger();
    }

    public static final UrlEntry of( String longUrl ){
        return new UrlEntry( longUrl );
    }

    public final String getLongUrl(){
        return longUrl;
    }

    public final int getCount(){
        return count.get();
    }

    public void incrementCount() {
        count.incrementAndGet();
    }

    @Override
    public final String toString(){
        return longUrl + ", Count: " + getCount();
    }

}
