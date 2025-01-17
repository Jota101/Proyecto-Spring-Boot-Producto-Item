package com.formacionbdi.springboot.app.item;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {
	
	@Bean
	RestTemplate registrarRestTemplate() {
		//RestTemplate sirve para consumir API's externas
		return new RestTemplate();
	}

    //Configuracion RS4J
    @Bean
    Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
		/*Customizer<Resilience4JCircuitBreakerFactory> ca;
		ca = id -> id.addCircuitBreakerCustomizer(null, null);
		Customizer fac = new Customizer() {

			@Override
			public void customize(Object factory) {
				Resilience4JCircuitBreakerFactory fac1 = (Resilience4JCircuitBreakerFactory) factory;
				fac1.configureDefault(id -> {
					return new Resilience4JConfigBuilder(id).circuitBreakerConfig(CircuitBreakerConfig.custom()
							.slidingWindowSize(10)
							.failureRateThreshold(50)
							.waitDurationInOpenState(Duration.ofSeconds(10L))
							.build())
							.timeLimiterConfig(TimeLimiterConfig.ofDefaults())
							.build();
				});
			}
			
		};*/
		return factory -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id).circuitBreakerConfig(CircuitBreakerConfig.custom()
					.slidingWindowSize(10)
					.failureRateThreshold(50)
					.waitDurationInOpenState(Duration.ofSeconds(10L))
					.build())
					.timeLimiterConfig(TimeLimiterConfig.ofDefaults())
					.build();
		});
	}
}
