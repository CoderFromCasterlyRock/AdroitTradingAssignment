package com.adroit.trading.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


public class TestUrlCustomPersistence {

    private Persister persister;

    private static final Map<String, String> urlMap = Map.of(
            "g12", "www.google.com",
            "y12", "www.yahoo.com",
            "t12", "www.twitter.com/news",
            "a12", "www.adroit-tt.com",
            "c12", "www.cnn.com");


    @Before
    public void init(){
        persister = new UrlInMemoryPersister( );
    }


    @Test
    public void create(){
        for( var entry : urlMap.entrySet() ){
            var shortUrl = persister.generateCustomMapping(entry.getKey(), entry.getValue()).get();
            assertThat(shortUrl).isEqualTo(entry.getKey());
            assertThat(entry.getValue()).isEqualTo(persister.get(shortUrl).get());
        }

        assertThat(persister.getSize() ).isEqualTo(urlMap.size());
        assertThat(persister.getEntryStream().count() ).isEqualTo(urlMap.size());
    }


    @Test
    public void create_and_lookup(){
        for( var entry : urlMap.entrySet() ){
            var shortUrl = persister.generateCustomMapping(entry.getKey(), entry.getValue()).get();
            assertThat(persister.get(shortUrl).get()).isEqualTo(entry.getValue());
        }
    }


    @Test
    public void create_and_delete(){
        for( var entry : urlMap.entrySet() ){
            persister.generateCustomMapping(entry.getKey(), entry.getValue()).get();
        }

        persister.remove("t12");
        persister.remove("c12");

        assertThat(persister.get("t12").isEmpty()).isTrue();
        assertThat(persister.get("c12").isEmpty()).isTrue();
        assertThat(persister.get("y12").isEmpty()).isFalse();

        assertThat(persister.getSize() ).isEqualTo(3);
        assertThat(persister.getEntryStream().count() ).isEqualTo(3);

    }


    @Test
    public void double_mapping(){
        var googleUrl = "www.google.com";
        var shortUrl1 = persister.generateCustomMapping("g1", googleUrl).get();
        var shortUrl2 = persister.generateCustomMapping("g1", googleUrl).get();

        assertThat(shortUrl2).isEqualTo("g1");
        assertThat(persister.getSize() ).isEqualTo(1);
        assertThat(persister.getEntryStream().count() ).isEqualTo(1);
        assertThat(persister.get(shortUrl1).get()).isEqualTo(googleUrl);
    }


    @Test
    public void double_mapping_conflict(){
        var googleUrl = "www.google.com";
        var shortUrl1 = persister.generateCustomMapping("g1", googleUrl).get();
        assertThat(shortUrl1).isEqualTo("g1");

        var shortUrl2 = persister.generateCustomMapping("g1", "www.twitter.com");
        assertThat(shortUrl2.isEmpty()).isTrue();

        assertThat(persister.getSize() ).isEqualTo(1);
        assertThat(persister.getEntryStream().count() ).isEqualTo(1);
        assertThat(persister.get(shortUrl1).get()).isEqualTo(googleUrl);
    }


    @Test
    public void mapping_single_long_url_to_multiple_short_urls(){
        var longUrl             = "www.google.com";
        var customShortUrl      = "tiny.ly/g1";
        var generatedShortUrl = persister.generateCustomMapping(customShortUrl, longUrl).get();
        assertThat(generatedShortUrl).isEqualTo(customShortUrl);
        assertThat(persister.get(customShortUrl).get()).isEqualTo(longUrl);

        var shortUrl = persister.generateMapping(longUrl);
        assertThat(persister.get(shortUrl.get()).get()).isEqualTo(longUrl);

        assertThat(persister.getSize() ).isEqualTo(2);
        assertThat(persister.getEntryStream().count() ).isEqualTo(2);
    }


    @Test
    public void stats(){
        String google = persister.generateCustomMapping("g1", "www.google.com").get();
        String yahoo = persister.generateCustomMapping("y1", "www.yahoo.com").get();
        String stackoverflow = persister.generateCustomMapping("s1", "www.stackoverflow.com").get();

        persister.get(yahoo);
        persister.get(yahoo);
        persister.get(yahoo);
        persister.get(yahoo);
        persister.get(yahoo);

        persister.get(stackoverflow);

        var streamMap = persister.getEntryStream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        assertThat(streamMap.get(google).getCount() ).isEqualTo(0);
        assertThat(streamMap.get(stackoverflow).getCount() ).isEqualTo(1);
        assertThat(streamMap.get(yahoo).getCount() ).isEqualTo(5);
        assertThat(persister.getSize() ).isEqualTo(3);
        assertThat(persister.getEntryStream().count() ).isEqualTo(3);
    }


    @After
    public void clean(){
        persister = null;
    }

}
