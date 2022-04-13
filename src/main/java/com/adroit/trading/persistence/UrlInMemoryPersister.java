package com.adroit.trading.persistence;

import com.adroit.trading.core.CommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.stream.Stream;

/**
 * Implements @link Persister interface by storing mappings in a HashMap in memory.
 * Not thread safe.
 */


public final class UrlInMemoryPersister extends Persister{

    private final Map<String, UrlEntry> shortMap;

    private static final int MAX_TRY_COUNT  = 5;
    private static final Logger LOGGER      = LoggerFactory.getLogger( CommandProcessor.class.getSimpleName() );

    public UrlInMemoryPersister( ){
        this( 2046 );
    }

    public UrlInMemoryPersister(int capacity ){
        super( new UrlGenerator() );

        this.shortMap   = new HashMap<>( capacity );
    }

    @Override
    public final int getSize( ){
        return shortMap.size();
    }


    @Override
    public final Stream<Map.Entry<String, UrlEntry>> getEntryStream(){
        return shortMap.entrySet().stream();
    }

    @Override
    public final Optional<String> get( String shortUrl ){
        var entry = shortMap.get(shortUrl);
        if( entry != null ){
            entry.incrementCount();
            LOGGER.debug("Found [{}] -> [{}] mapping.", shortUrl, entry.getLongUrl());
            return Optional.of(entry.getLongUrl());
        }

        LOGGER.debug("Get failed as [{}] isn't mapped to anything.", shortUrl);
        return Optional.empty();
    }


    @Override
    public final Optional<String> remove( String deleteUrl ){
        var entry = shortMap.remove(deleteUrl);
        if( entry == null ){
            LOGGER.debug("Remove failed as [{}] isn't mapped to anything.", deleteUrl);
            return Optional.empty();
        }else{
            LOGGER.debug("Removed [{}] -> [{}] mapping.", deleteUrl, entry.getLongUrl());
            return Optional.of(entry.getLongUrl());
        }
    }

    /**
     * Attempts to generate a unique 6 char length url.
     * Abort if we fail to generate a unique url in 5 attempts.
     *
     * @param longUrl
     * @return
     */
    @Override
    public final Optional<String> generateMapping( String longUrl ){

        for( int i=1; i<=MAX_TRY_COUNT; i++ ){
            var shortUrl  = generateShortUrl();
            var oldValue= shortMap.putIfAbsent(shortUrl, UrlEntry.of(longUrl));
            if( oldValue == null ){
                LOGGER.debug("Mapped [{}] -> [{}] in [{}] attempts.", shortUrl, longUrl, i);
                return Optional.of(shortUrl);
            }
        }

        LOGGER.error("Failed to map [{}] to a unique short url in [{}] attempts.", longUrl, MAX_TRY_COUNT);
        return Optional.empty();
    }


    @Override
    public final Optional<String> generateCustomMapping( String shortUrl, String longUrl ){

        var oldValue= shortMap.putIfAbsent(shortUrl, UrlEntry.of(longUrl));
        if( oldValue == null ){
            LOGGER.debug("Stored mapping [{}] -> [{}]", shortUrl, longUrl);
            return Optional.of(shortUrl);
        }

        if( longUrl.equals(oldValue.getLongUrl()) ){
            LOGGER.debug("Same mapping already exists for [{}] -> [{}]", shortUrl, longUrl);
            return Optional.of(shortUrl);
        }

        LOGGER.error("Failed as user provided short url [{}] is already mapped to [{}]!", shortUrl, oldValue);
        return Optional.empty();

    }

}
