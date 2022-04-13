package com.adroit.trading.core;

import com.adroit.trading.operations.*;
import com.adroit.trading.persistence.Persister;
import com.adroit.trading.util.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class CommandProcessor implements Runnable{

    private final Console console;
    private final Persister persister;
    private final ExecutorService service;

    private static final Logger LOGGER = LoggerFactory.getLogger( CommandProcessor.class.getSimpleName() );

    public CommandProcessor( Persister persister ){
        this.persister  = persister;
        this.console    = System.console();
        this.service    = Executors.newSingleThreadExecutor( new NamedThreadFactory("CommandProcessor"));
    }


    public final void start( ){
        service.submit(this);
    }


    @Override
    public final void run(){

        CommandType.showOptions();

        AbstractCommand command = null;
        do{
            try{
                System.out.print( ">> ");
                command = parseCommand( console.readLine() );
                System.out.println( command.process() );

            }catch(Exception e ){
                LOGGER.error("Exception while handling input", e);
                CommandType.showOptions();
            }

        }while( command.getCommandType() != CommandType.QUIT_APP);

    }


    public final AbstractCommand parseCommand( String inputCommand ){

        AbstractCommand command= null;

        try {

            String[] tokens = inputCommand.split("\\s+");
            CommandType type= CommandType.lookup(tokens);
            LOGGER.debug("User entered [{}], converted to command [{}]", inputCommand, type);

            switch (type) {

                case CREATE_URL:
                    command = new CreateUrlCommand(tokens, persister);
                    break;

                case DELETE_URL:
                    command = new DeleteUrlCommand(tokens, persister);
                    break;

                case LOOKUP_URL:
                    command = new LookupUrlCommand(tokens, persister);
                    break;

                case GET_STATS:
                    command = new StatsCommand(persister);
                    break;

                case GET_HELP:
                    command = new HelpCommand();
                    break;

                case QUIT_APP:
                    command = new QuitCommand();
                    break;

                default:
                    command = new UnknownCommand();
                    break;
            }

        }catch (Exception e ){
            LOGGER.error("Failed to parse command [{}]", inputCommand, e);
            command = new MalformedCommand();
        }

        return command;

    }


    public final void stop(){
        service.shutdown();
        LOGGER.info("Stopped Command processor thread.");
    }


}
