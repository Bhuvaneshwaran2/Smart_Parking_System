package com.smartparking.config;
import com.smartparking.service.ParkingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SeedRunner {
 @Bean CommandLineRunner seed(ParkingService service){return args -> service.seedIfEmpty();}
}
