package com.nyu.Jobs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;

public class ServerVerticle extends AbstractVerticle {
    private final EventBus eventBus;
    private final Vertx vertx;
    private String loginResponse = "false";
    
    public ServerVerticle(Vertx vertx) {
        this.vertx = vertx;
        this.eventBus = vertx.eventBus();        
    }
    
    @Override
    public void start() throws Exception {
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(req -> {
            
            String requestType = req.getParam("type");
            
            if (requestType.equals("login")) {
                EventBus eb = this.eventBus;
                eb.send("login", req.getParam("content"), res -> {
                    //if (ar.succeeded()) ?
                    if (res.result().toString().equals("true")) {
                        loginResponse = "true";
                    }
                    else {
                        loginResponse = "false";
                    }
                });
            }
            req.response()
            .putHeader("content-type", "text/plain")
            .end(loginResponse);
        }).listen(8080);
        System.out.println("HTTP server started on port 8080");
    }
}
