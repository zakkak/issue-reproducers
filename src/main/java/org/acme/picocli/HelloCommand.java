package com.acme.picocli;

import picocli.CommandLine;

import java.lang.reflect.Field;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import io.netty.util.internal.PlatformDependent;

@CommandLine.Command 
public class HelloCommand implements Runnable {

    @CommandLine.Option(names = {"-n", "--name"}, description = "Who will we greet?", defaultValue = "World")
    String name;

    private final GreetingService greetingService;

    public HelloCommand(GreetingService greetingService) { 
        this.greetingService = greetingService;
    }

    @Override
    public void run() {
        greetingService.sayHello(name);
    }
}

@Dependent
class GreetingService {

    static final long maxDirectMemoryStatic = PlatformDependent.maxDirectMemory();

    void sayHello(String name) {
        System.out.println("maxDirectMemoryStatic= " + maxDirectMemoryStatic/1024/1024/1024 + " GB");
        long maxDirectMemory = PlatformDependent.maxDirectMemory();
        System.out.println("maxDirectMemory= " + maxDirectMemory/1024/1024/1024 + " GB");
        System.out.println("Hello " + name + "!");
    }
}