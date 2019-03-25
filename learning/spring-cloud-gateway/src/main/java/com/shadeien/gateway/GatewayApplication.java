package com.shadeien.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
@EnableWebFluxSecurity
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class);
    }


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .matchers(EndpointRequest.toAnyEndpoint()).authenticated()
                .pathMatchers("/**/feign/**").denyAll()
                .pathMatchers("/**").permitAll()
                .and().formLogin()
                .and().csrf().disable()
                .build();
    }

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(WebSecurity web) {
//        web.ignoring().
//        return http
//                .authorizeExchange()
////                .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
////                .pathMatchers("/userservice/**").permitAll()
//                .matchers(EndpointRequest.toAnyEndpoint()).authenticated()
//                .pathMatchers("/**").permitAll()
//                .and().httpBasic()
//                .and().csrf().disable()
//                .build();
//    }
}
