package com.adroit.trading.operations;

import com.adroit.trading.persistence.Persister;

import static com.adroit.trading.util.TradingUtil.*;
import static com.adroit.trading.operations.CreateUrlCommand.MappingType.*;

/**
 *
 * Handles two ways of generating a unique short url given a long url.
 *
 * a) User can enter a long url, and we generate the unique short url.
 * b) User enters both long and short url.
 *
 * In either cases, we enforce uniqueness on the short url and then persist the mapping.
 *
 * Not thread safe
 *
 */
public final class CreateUrlCommand extends AbstractCommand{

    public enum MappingType{
        REGULAR,    //User enters the long url and we generate the short url.
        CUSTOM;     //User enters the long as well as the short url.
    }

    private final String longUrl;
    private final String shortUrl;
    private final MappingType type;
    private final Persister persister;

    public CreateUrlCommand( String[] tokens, Persister persister ){
        super( CommandType.CREATE_URL);

        this.persister  = persister;
        this.type       = determineType(tokens);
        this.longUrl    = parseLongUrl(tokens);
        this.shortUrl   = parseShortUrl(tokens);

    }

    @Override
    public final String process(){

        switch (type){
            default:
            case REGULAR: {
                var result = persister.generateMapping(longUrl);
                return result.isPresent() ? result.get() : "Internal Error: Failed to generate url";
            }

            case CUSTOM: {
                var result = persister.generateCustomMapping(shortUrl, longUrl);
                return result.isPresent() ? result.get() : "Internal Error: Failed to generate custom url";
            }
        }

    }


    private static final MappingType determineType( String[] tokens ){
        validate(tokens);
        return tokens.length == 2 ? MappingType.REGULAR : MappingType.CUSTOM;
    }


    /**
     * User can either enter
     * cu www.google.com OR cu customShortUrl www.google.com
     *
     * @param tokens
     * @return
     */
    public final String parseLongUrl( String[] tokens ){
        return (REGULAR == type) ? getCleanUrl(1, tokens) : getCleanUrl(2, tokens);
    }


    /**
     * User has entered a long url AND a custom short url
     * It must be non-empty + short url must have our domain
     *
     * @param tokens
     * @return
     */
    private final String parseShortUrl( String[] tokens ){
        if( MappingType.CUSTOM != type ) {
            return null;
        }

        String url = getCleanUrl(1, tokens);
        if( !url.startsWith(SHORT_URL_PREFIX) ){
            throw new IllegalStateException("Provided short url [" + url + "] must start with domain " + SHORT_URL_PREFIX);
        }

        return url.trim();
    }


}
