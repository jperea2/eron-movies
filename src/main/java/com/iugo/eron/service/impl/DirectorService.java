package com.iugo.eron.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.iugo.eron.dto.CustomPageImpl;
import com.iugo.eron.dto.Movie;
import com.iugo.eron.service.IDirectorService;

@Service
public class DirectorService implements IDirectorService<Movie> {
	
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DirectorService.class);
	
	@Value("${services.directors.url:'https://eron-movies.wiremockapi.cloud/api/movies/search?page=%s'}")
	private String urlService;
	private RestTemplate restTemplate;
	private RetryTemplate retryTemplate;
	
	@Autowired
	public DirectorService(RestTemplate restTemplate, RetryTemplate retryTemplate) {		
		this.restTemplate = restTemplate;
		this.retryTemplate = retryTemplate;
	}
	
	public List<String> getDirectors(int threshold) throws InterruptedException, ExecutionException {
		List<String> directors = getAllPages().get().stream()
					.flatMap(CustomPageImpl::get)
					.map(Movie::getDirector)
					.collect(Collectors.toList());

		 return directors.stream()
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
			.entrySet().stream()
				.filter(entry -> entry.getValue() > threshold)
				.map(Map.Entry::getKey)
				.sorted()
				.collect(Collectors.toList());
	}
	
	@Override
	public CompletableFuture<CustomPageImpl<Movie>> getByNumberPage(int numberPage) {
		try {
			String url = String.format(urlService, numberPage);
			ResponseEntity<CustomPageImpl<Movie>> response = 
				retryTemplate.execute(retryContext -> {
					LOGGER.info("Consuming service [Attemp={}][Url={}]", retryContext.getRetryCount(), url);
					return restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<CustomPageImpl<Movie>>(){});
				});		
			return CompletableFuture.completedFuture(response.getBody());
		} catch (RestClientException  e) {
			LOGGER.error("Error consuming service: {}", e.getMessage());
			return CompletableFuture.completedFuture(new CustomPageImpl<>(1, 1, 0L, 0, Collections.emptyList()));
		}
	}
	
	public void setUrlService(String urlService) {
		this.urlService = urlService;
	}
}