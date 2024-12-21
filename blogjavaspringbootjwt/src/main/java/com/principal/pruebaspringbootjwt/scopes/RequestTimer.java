package com.principal.pruebaspringbootjwt.scopes;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestTimer {

    private final long startTime;
    private long controllerStartTime;

    public RequestTimer() {
        this.startTime = System.currentTimeMillis();
    }

    public void setControllerStartTime() {
        this.controllerStartTime = System.currentTimeMillis();
    }

    public long getControllerDuration() {
        return System.currentTimeMillis() - controllerStartTime;
    }

    public long getRequestDuration() {
        return System.currentTimeMillis() - startTime;
    }


}
