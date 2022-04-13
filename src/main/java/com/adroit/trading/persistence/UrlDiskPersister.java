package com.adroit.trading.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * Just to illustrate how we would plug-in a different persistence mechanism.
 * NOT functional at the moment!
 *
 */
public final class UrlDiskPersister extends Persister{

    public UrlDiskPersister( ){
        super( null );
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Stream<Map.Entry<String, UrlEntry>> getEntryStream() {
        return Stream.empty();
    }

    @Override
    public Optional<String> get(String shortUrl ){
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> remove( String deleteUrl ){
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> generateMapping( String longUrl ){
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<String> generateCustomMapping( String shortUrl, String longUrl ){
        throw new UnsupportedOperationException();
    }


}
