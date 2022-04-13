package com.adroit.trading.operations;

import java.util.HashMap;
import java.util.Map;

public enum CommandType{

    CREATE_URL("cu", "create url", "cu www.google.com OR cu tiny.ly/abc123 www.google.com"),
    DELETE_URL("du", "delete url", "du bit.ly/abc123"),
    LOOKUP_URL("lu", "lookup url", "lu bit.ly/abc123"),
    GET_STATS("gs", "get stats", "gs"),
    GET_HELP("gh", "get help", "gh"),
    QUIT_APP("qa", "quit app", "qa"),
    MALFORMED("", "", "" ),
    UNKNOWN("", "", "" );

    private final String value;
    private final String description;
    private final String instructions;

    private static final Map<String, CommandType> REVERSE_MAP = new HashMap<>();

    static{
        for( CommandType input : CommandType.values() ){
            REVERSE_MAP.put(input.value, input);
            REVERSE_MAP.put(input.value.toUpperCase(), input);
        }
    }


    private CommandType( String value, String description, String instructions ){
        this.value  = value;
        this.description = description;
        this.instructions= instructions;
    }


    public final String getValue( ){
        return value;
    }

    public static final CommandType lookup(String[] tokens){
        return REVERSE_MAP.getOrDefault(tokens[0], UNKNOWN);
    }

    public static final String getOptions( ){
        StringBuilder builder = new StringBuilder( );
        builder.append("\n");
        builder.append("Usage:");
        builder.append("\n");

        for( CommandType type : CommandType.values() ) {
            if( !type.value.isBlank() ) {
                builder.append("[").append(type.description).append("]")
                .append("\t").append("[").append(type.instructions).append("]")
                .append("\n");
            }
        }

       return builder.toString();

    }


    public static final void showOptions( ){
        System.out.println(getOptions());
    }


}

