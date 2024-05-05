package com.svalero;

import com.svalero.domain.Event;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        WebClient webclient = WebClient.create("http://localhost:8082");

        Flux<Event> eventFlux = webclient.get()
                .uri("/events")
                .retrieve()
                .bodyToFlux(Event.class);

        eventFlux
                .subscribeOn(Schedulers.fromExecutor(Executors.newCachedThreadPool()))
                .doOnComplete(() -> System.out.println("Fin"))
                .subscribe(event -> System.out.println(event.getCode()));

    }
}
