package com.example.ApiGateway.AuthenticationFilter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String validateTokenUrl = "http://localhost:8090/api/v1/authentication/validateToken";

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        WebClient webClient = webClientBuilder.build();

        return (exchange, chain) -> {
            var request = exchange.getRequest();

            // Extract JWT from the Authorization header
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (token == null || !token.startsWith("Bearer ")) {
                return chain.filter(exchange);
            }

            // Remove "Bearer " prefix
            token = token.substring(7);

            // Send the token to the authentication service for validation
            return webClient.post()
                    .uri(validateTokenUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(isValid -> {
                        if (Boolean.TRUE.equals(isValid)) {
                            return chain.filter(exchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    });
        };
    }

    public static class Config {
        // Put configuration properties here
    }
}


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public JwtAuthenticationFilter() {
//        super(Config.class);
//    }
//    private final String validateToken_Url = "http://localhost:8090/api/v1/authentication/validateToken";
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//
//            // Extract JWT from the Authorization header
//            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//            if (token == null || !token.startsWith("Bearer ")) {
//                return chain.filter(exchange);
//            }
//
//            // Remove "Bearer " prefix
//            token = token.substring(7);
//
//            // Send the token to the authentication service for validation
//            HttpHeaders headers = new HttpHeaders();
//            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(
//                    validateToken_Url, // Authentication service URL
//                    entity,
//                    Boolean.class
//            );
//
//            boolean isValid = Boolean.TRUE.equals(responseEntity.getBody());
//
//            if (isValid) {
//                return chain.filter(exchange);
//            } else {
//                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                return exchange.getResponse().setComplete();
//            }
//        };
//    }
//
//    public static class Config {
//        // Put configuration properties here
//    }
//}
//
