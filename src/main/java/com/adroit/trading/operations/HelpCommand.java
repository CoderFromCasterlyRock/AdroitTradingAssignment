package com.adroit.trading.operations;


public final class HelpCommand extends AbstractCommand{

    public HelpCommand( ){
        super( CommandType.GET_HELP );
    }

    @Override
    public final String process(){
        return CommandType.getOptions();
    }

}
