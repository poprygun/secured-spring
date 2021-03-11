package io.microsamples.security.securechachies.conf;

import java.util.concurrent.atomic.AtomicInteger;

public class Chachkie {
    private AtomicInteger sentiment = new AtomicInteger(0);
    private String name;

    public Integer getSentiment() {
        return sentiment.get();
    }

    public String getName() {
        return name;
    }

    public Chachkie(String name) {
        this.name = name;
    }


    public Chachkie up() {
        sentiment.incrementAndGet();
        return this;
    }

    public Chachkie down() {
        sentiment.decrementAndGet();
        return this;
    }
}
