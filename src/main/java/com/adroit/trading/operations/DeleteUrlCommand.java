package com.adroit.trading.operations;

import com.adroit.trading.persistence.Persister;

import static com.adroit.trading.util.TradingUtil.*;


public final class DeleteUrlCommand extends AbstractCommand{

    private final String deleteUrl;
    private final Persister persister;

    public DeleteUrlCommand(String[] tokens, Persister persister){
        super( CommandType.DELETE_URL);

        this.persister  = persister;
        this.deleteUrl  = parseDeleteUrl(tokens);
    }

    @Override
    public final String process(){
        var result = persister.remove(deleteUrl);
        return result.isPresent() ? result.get() : (deleteUrl + " doesn't exist");
    }

    private static final String parseDeleteUrl( String[] tokens ){
        validate( tokens );

        String url = getCleanUrl(1, tokens);
        if( !url.startsWith(SHORT_URL_PREFIX) ){
            return url + " must start with " + SHORT_URL_PREFIX;
        }

        return url;
    }


}
