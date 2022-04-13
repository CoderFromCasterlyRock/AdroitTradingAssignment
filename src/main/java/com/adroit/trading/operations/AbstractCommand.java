package com.adroit.trading.operations;


public abstract class AbstractCommand {

    private final CommandType type;

    public AbstractCommand(CommandType type ){
        this.type = type;
    }

    public abstract String process();

    public final CommandType getCommandType( ){
        return type;
    }


}
