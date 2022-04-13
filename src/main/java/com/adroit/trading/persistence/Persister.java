package com.adroit.trading.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.adroit.trading.util.TradingUtil.*;


public abstract class Persister {

    private final UrlGenerator generator;

    public Persister( UrlGenerator generator ){
        this.generator = generator;
    }

    public abstract int getSize( );
    public abstract Stream<Map.Entry<String, UrlEntry>> getEntryStream();
    public abstract Optional<String> get( String shortUrl );
    public abstract Optional<String> remove( String deleteUrl );
    public abstract Optional<String> generateMapping( String longUrl );
    public abstract Optional<String> generateCustomMapping( String shortUrl, String longUrl );


    protected final String generateShortUrl( ){
        return SHORT_URL_PREFIX + FORWARD_SLASH + generator.generateRandomUrl();
    }

}
