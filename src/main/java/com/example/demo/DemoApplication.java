package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@RestController
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @GetMapping("/")
  public String hello() {
    return "Hello from New Relic Java EC2 demo!";
  }

  // Generate some work to show up in APM
  @GetMapping("/work")
  public String work(@RequestParam(defaultValue = "25") int n) throws InterruptedException {
    // fake CPU + latency
    long count = 0;
    for (int i = 0; i < n * 100_000; i++) {
      count += ThreadLocalRandom.current().nextInt(1, 10);
    }
    Thread.sleep(Math.min(1000, n * 10L));
    return "Work done: " + count;
  }

  // Trigger an error to see it in APM
  @GetMapping("/error")
  public String boom() {
    throw new RuntimeException("Boom! Intentional error for New Relic demo.");
  }
}