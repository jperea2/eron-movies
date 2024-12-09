package com.iugo.eron.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class BeansConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
		RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
		restTemplate.getMessageConverters().add(0, converter);
		return restTemplate;
	}
	
	@Bean
    public RetryTemplate retryTemplate(@Value("${services.retries: 1}") int retries,
    		@Value("${servcies.period-retry: 1000}") long periodRetry) {
        RetryTemplate retryTemplate = new RetryTemplate();
		
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(periodRetry);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(retries);
        retryTemplate.setRetryPolicy(retryPolicy);
		
        return retryTemplate;
    }
	
	 @Bean
     public OpenAPI customOpenAPI() {
         return new OpenAPI()
                 .info(new Info()
                 .title("Movie Directors API")
                 .version("1.0")
                 .description("API documentation for Movie Directors application"));
     }
}
