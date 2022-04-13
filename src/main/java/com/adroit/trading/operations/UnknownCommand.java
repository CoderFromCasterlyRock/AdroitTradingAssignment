package com.adroit.trading.operations;


public final class UnknownCommand extends AbstractCommand{


    public UnknownCommand( ){
        super( CommandType.UNKNOWN );
    }

    @Override
    public final String process(){
        return "Unknown Command! Type in " + CommandType.GET_HELP.getValue() + " for help.";
    }

}
