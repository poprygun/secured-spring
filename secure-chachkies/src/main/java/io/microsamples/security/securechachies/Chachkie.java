package io.microsamples.security.securechachies;

import java.util.concurrent.atomic.AtomicInteger;

public class Chachkie {
    private AtomicInteger chachkie = new AtomicInteger(0);
    private String name;

    public Integer getChachkie() {
        return chachkie.get();
    }

    public String getName() {
        return name;
    }

    public Chachkie(String name) {
        this.name = name;
    }


    public Chachkie up() {
        chachkie.incrementAndGet();
        return this;
    }

    public Chachkie down() {
        chachkie.decrementAndGet();
        return this;
    }
}
