package com.pallav.InmemoryTwitter;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoTest  {

    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Boot with Java 21!";
    }
}