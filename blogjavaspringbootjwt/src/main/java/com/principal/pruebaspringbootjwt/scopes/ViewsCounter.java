package com.principal.pruebaspringbootjwt.scopes;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.concurrent.atomic.AtomicLong;

@Component
@ApplicationScope
public class ViewsCounter {

    private final AtomicLong counter = new AtomicLong();

    public long increment() {
        return counter.incrementAndGet();
    }

    public long getCount() {
        return counter.get();
    }
}

