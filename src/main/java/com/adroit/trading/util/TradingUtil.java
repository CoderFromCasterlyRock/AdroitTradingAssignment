package com.adroit.trading.util;


public final class TradingUtil {

    private TradingUtil() {}

    public static final String FORWARD_SLASH    = "/";
    public static final String SHORT_URL_PREFIX = "tiny.ly";


    public static final String[] validate( String[] tokens ){
        if( tokens.length < 2 || tokens.length > 3){
            throw new IllegalStateException("Command is malformed.");
        }

        return tokens;
    }


    public static final String getCleanUrl( int index, String[] tokens ){
        String url = tokens[index];
        if( url == null || url.isBlank() ){
            throw new IllegalStateException(url + " is malformed.");
        }

        return url.trim();
    }

}
