package com.adroit.trading.operations;

import com.adroit.trading.persistence.Persister;

import static com.adroit.trading.util.TradingUtil.*;


public final class LookupUrlCommand extends AbstractCommand{

    private final String lookupUrl;
    private final Persister persister;

    public LookupUrlCommand(String[] tokens, Persister persister ){
        super( CommandType.LOOKUP_URL);

        this.persister  = persister;
        this.lookupUrl  = parseLookupUrl(tokens);
    }


    @Override
    public final String process(){
        var result = persister.get( lookupUrl );
        return result.isPresent() ? result.get() : "Mapping doesn't exist for " + lookupUrl;
    }


    private static final String parseLookupUrl( String[] tokens ){
        validate(tokens);

        String url = getCleanUrl(1, tokens);
        return url.startsWith(SHORT_URL_PREFIX) ? url : (SHORT_URL_PREFIX + url);
    }

}
