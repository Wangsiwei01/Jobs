package com.nyu.Jobs;

import org.json.JSONObject;

import com.nyu.Utils.PasswordHashing;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class LoginVerticle extends AbstractVerticle {
    private final EventBus eventBus;
    private final Vertx vertx;
    
    public LoginVerticle(Vertx vertx) {
        this.vertx = vertx;
        eventBus = vertx.eventBus();
    }
    @Override
    public void start() throws Exception {
        MessageConsumer<String> consumer = eventBus.consumer("login");
        System.out.println("Listening to login...");
        consumer.handler(message -> {
            String body = message.body();
            JSONObject jobj = new JSONObject(body);
            if (jobj != null) {
                String pass = (String) jobj.get("password");
                pass = PasswordHashing.getPasswordAfterHashing(pass);
                System.out.println("PassAfterHashing:" + pass);
            }
            System.out.println("I have received a message: " + body);
            message.reply("true");
        });        
    }
}
