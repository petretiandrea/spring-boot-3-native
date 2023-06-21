package com.example.reactive.demoreactivenative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.function.Consumer;

@SpringBootApplication
@ImportRuntimeHints(ReflectionConfig.class)
public class DemoReactiveNativeApplication {

    @Bean
    Consumer<String> myConsumer() {
        return (payload) -> System.out.println("Consumed " + payload);
    }

    public static void main(String[] args) {
        final var context = SpringApplication.run(DemoReactiveNativeApplication.class, args);
        final var bridge = context.getBean(StreamBridge.class);
        bridge.send("supplier-out-0", "initialized");
    }

}
