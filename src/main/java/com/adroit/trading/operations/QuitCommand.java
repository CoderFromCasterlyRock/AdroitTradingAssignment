package com.adroit.trading.operations;


public final class QuitCommand extends AbstractCommand{

    public QuitCommand( ){
        super( CommandType.QUIT_APP);
    }

    @Override
    public final String process(){
        return "Good Bye!";
    }

}
