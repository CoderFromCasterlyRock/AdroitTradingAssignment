package com.adroit.trading.persistence;

import com.adroit.trading.util.TradingUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class TestUrlPersistence {

    private Persister persister;

    private static final List<String> urlList = List.of("www.google.com", "www.yahoo.com", "www.twitter.com/news", "www.adroit-tt.com", "www.cnn.com");


    @Before
    public void init(){
        persister = new UrlInMemoryPersister( );
    }

    @Test
    public void create(){
        for( String longUrl : urlList ){
            String shortUrl = persister.generateMapping(longUrl).get();
            assertThat(shortUrl).isNotEmpty();
            assertThat(shortUrl).isNotEqualTo(longUrl);
            assertThat(shortUrl.startsWith(TradingUtil.SHORT_URL_PREFIX));
        }

        assertThat(persister.getSize() ).isEqualTo(urlList.size());
        assertThat(persister.getEntryStream().count() ).isEqualTo(urlList.size());
    }


    @Test
    public void create_and_lookup(){
        for( String longUrl : urlList ){
            String shortUrl = persister.generateMapping(longUrl).get();
            assertThat(persister.get(shortUrl).get()).isEqualTo(longUrl);
        }
    }


    @Test
    public void create_and_delete(){
        var yahooUrl = persister.generateMapping("www.yahoo.com").get();
        var googleUrl = persister.generateMapping("www.google.com").get();
        var twitterUrl = persister.generateMapping("www.twitter.com/news").get();

        persister.remove(yahooUrl);
        persister.remove(twitterUrl);

        assertThat(persister.get(yahooUrl).isEmpty()).isTrue();
        assertThat(persister.get(twitterUrl).isEmpty()).isTrue();
        assertThat(persister.get(googleUrl).isEmpty()).isFalse();

        assertThat(persister.getSize() ).isEqualTo(1);
    }


    @Test
    public void stats(){
        String google = persister.generateMapping("www.google.com").get();
        String yahoo = persister.generateMapping("www.yahoo.com").get();
        String twitter = persister.generateMapping("www.twitter.com").get();
        String cnn = persister.generateMapping("www.cnn.com").get();

        for( int i=0; i< 100; i++ ){
            persister.get(yahoo);
        }

        for( int i=0; i< 50; i++ ){
            persister.get(google);
        }

        persister.get(twitter);

        var streamMap = persister.getEntryStream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        assertThat(streamMap.get(cnn).getCount() ).isEqualTo(0);
        assertThat(streamMap.get(twitter).getCount() ).isEqualTo(1);
        assertThat(streamMap.get(google).getCount() ).isEqualTo(50);
        assertThat(streamMap.get(yahoo).getCount() ).isEqualTo(100);

        assertThat(persister.getSize() ).isEqualTo(4);
        assertThat(persister.getEntryStream().count() ).isEqualTo(4);

    }


    @After
    public void clean(){
        persister = null;
    }

}
