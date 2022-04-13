package com.adroit.trading.operations;


public final class MalformedCommand extends AbstractCommand{

    public MalformedCommand( ){
        super( CommandType.MALFORMED );
    }

    @Override
    public final String process(){
        return "Command is malformed! Type in " + CommandType.GET_HELP.getValue() + " for help.";
    }

}
