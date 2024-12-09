package com.iugo.eron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.iugo.eron.dto.CustomPageImpl;
import com.iugo.eron.dto.Movie;
import com.iugo.eron.service.impl.DirectorService;

public class DirectorServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RetryTemplate retryTemplate;

    @InjectMocks
    private DirectorService directorService;
    
    private CustomPageImpl<Movie> page;

    @BeforeEach
    public void setUp() throws Throwable {
		MockitoAnnotations.openMocks(this);
		directorService.setUrlService("https://eron-movies.wiremockapi.cloud/api/movies/search?page=%s");
		
		Movie movie1 = new Movie("Title A", 2021, "PG-13", "10 Jun 2011", "94 min", "Comedy, Fantasy, Romance", "Director A", "Woody Allen", "Owen Wilson, Rachel McAdams, Kathy Bates");
		Movie movie2 = new Movie("Title B", 2021, "PG-13", "10 Jun 2011", "94 min", "Comedy, Fantasy, Romance", "Director B", "Woody Allen", "Owen Wilson, Rachel McAdams, Kathy Bates");
		Movie movie3 = new Movie("Title C", 2021, "PG-13", "10 Jun 2011", "94 min", "Comedy, Fantasy, Romance", "Director A", "Woody Allen", "Owen Wilson, Rachel McAdams, Kathy Bates");
		
		page = new CustomPageImpl<>(1, 3, 3L, 1, Arrays.asList(movie1, movie2, movie3));
 
		ResponseEntity<CustomPageImpl<Movie>> responseEntity = ResponseEntity.ok(page);
		when(retryTemplate.execute(any())).thenReturn(responseEntity);

		when(directorService.getAllPages()).thenReturn(CompletableFuture.completedFuture(Arrays.asList(page)));
    }

    @Test
    public void testGetDirectors() throws InterruptedException, ExecutionException {
        List<String> result = directorService.getDirectors(1);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Director A", result.get(0));
    }

    @Test
    public void testGetByNumberPage() throws Throwable {
        CompletableFuture<CustomPageImpl<Movie>> result = directorService.getByNumberPage(1);
        assertNotNull(result);
        assertEquals(page, result.join());
    }
    
    @Test
    public void testGetByNumberPage_RestClientException() throws Throwable {
    	when(retryTemplate.execute(any())).thenThrow(new RestClientException("Service error"));
        CompletableFuture<CustomPageImpl<Movie>> result = directorService.getByNumberPage(1);
        assertNotNull(result);
        assertTrue(result.join().getContent().isEmpty());
    }
}
