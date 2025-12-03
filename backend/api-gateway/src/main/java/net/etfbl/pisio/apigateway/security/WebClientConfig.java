package net.etfbl.pisio.apigateway.security;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced   // <-- critical: tells Spring Cloud to resolve service names via Eureka
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
