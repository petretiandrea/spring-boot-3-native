package com.example.reactive.demoreactivenative;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class SimpleRestController {

    private final StreamBridge streamBridge;

    public SimpleRestController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PutMapping("/tryKafka")
    public Mono<ResponseEntity<String>> publishOnKafka(@RequestBody String body) {
        return Mono.fromCallable(() -> streamBridge.send("supplier-out-0", MessageBuilder.withPayload(body).build()))
                .subscribeOn(Schedulers.boundedElastic())
                .map(it -> ResponseEntity.ok(body));
    }

}
