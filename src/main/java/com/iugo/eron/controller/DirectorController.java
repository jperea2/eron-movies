package com.iugo.eron.controller;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iugo.eron.controller.swagger.ISwaggerDirectorController;
import com.iugo.eron.dto.ErrorMessage;
import com.iugo.eron.dto.Movie;
import com.iugo.eron.response.Director;
import com.iugo.eron.service.IDirectorService;

@RestController
@RequestMapping("/api")
public class DirectorController implements ISwaggerDirectorController {

	Logger LOGGER = LoggerFactory.getLogger(DirectorController.class);
	
	private IDirectorService<Movie> directorService;
	
	@Autowired
	public DirectorController(IDirectorService<Movie> directorService) {		
		this.directorService = directorService;
	}
	
	@GetMapping("/directors")
	public ResponseEntity<?> getDirectors(@RequestParam(value="threshold", required = true) int threshold ){
		try {
			return ResponseEntity.ok()
					.body(new Director(directorService.getDirectors(threshold)));
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("Error while consuming the service: {}", e.getMessage());
			return ResponseEntity.internalServerError()
				.body(new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}
}