package com.adroit.trading.core;

import com.adroit.trading.persistence.Persister;
import com.adroit.trading.persistence.UrlInMemoryPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class ApplicationController{

    private final Persister persister;
    private final CommandProcessor processor;

    private static final Logger LOGGER = LoggerFactory.getLogger( ApplicationController.class.getSimpleName() );

    public ApplicationController( ){
        this.persister = new UrlInMemoryPersister();
        this.processor = new CommandProcessor(persister);
    }


    public final void start( ){
        LOGGER.info("Starting Adroit Trading assignment.");
        processor.start();
    }



    public final void stop(){
        processor.stop();
        LOGGER.info("Successfully stopped Adroit Trading assignment.");
    }


}
