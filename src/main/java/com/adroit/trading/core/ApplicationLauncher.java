package com.adroit.trading.core;


public final class ApplicationLauncher{

    static{
        Thread.setDefaultUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException( Thread thread, Throwable ex ){
                System.err.println("CAUGHT unhandled exception in Thread " + thread.getName() );
                ex.printStackTrace();
            }
        });
    }


    public static void main( String[] args ){
        ApplicationController controller= new ApplicationController( );
        controller.start( );
    }


}
