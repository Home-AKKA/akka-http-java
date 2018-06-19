package com.example.akkahttp;

import akka.actor.ActorSystem;
import com.example.akkahttp.web.route.GroupRoute;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppExample extends AbstractModule {

    private static final Logger log = LoggerFactory.getLogger(AppExample.class);

    // 1. запускаем актор-систему
    private final static ActorSystem actorSystem = ActorSystem.create("system-http-example");

    @Override
    protected void configure() {
        // 2. биндим (добавляем) rest-метод(ы) в актор-систему
        bind(GroupRoute.class);
    }

    public static void main(String... args) {
        // 3. биндим хост и порт для запуска актор-системы
        Injector injector = Guice.createInjector(new AppExample());
        injector.getInstance(GroupRoute.class).bindRoute("localhost", 8080, actorSystem);

        log.debug("Запуск Actor-системы...");
//        log.debug("<ENTER> to exit");

//        System.in.read();
//        actorSystem.shutdown(); // to exit
    }
}