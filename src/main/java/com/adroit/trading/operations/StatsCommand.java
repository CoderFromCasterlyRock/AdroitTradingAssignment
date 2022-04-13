package com.adroit.trading.operations;

import com.adroit.trading.persistence.Persister;


public final class StatsCommand extends AbstractCommand{

    private final Persister persister;

    public StatsCommand( Persister persister ){
        super( CommandType.GET_STATS );

        this.persister = persister;
    }


    @Override
    public final String process(){
        StringBuilder builder = new StringBuilder( 8 * persister.getSize() );
        persister.getEntryStream().forEach(
                (x) -> builder.append(x.getKey())
                        .append("\t")
                        .append(x.getValue().getLongUrl())
                        .append("\t")
                        .append(x.getValue().getCount())
                        .append("\n")
        );

        String result = builder.toString();
        return result.isBlank() ? "Nothing to report" : result;
    }


}
