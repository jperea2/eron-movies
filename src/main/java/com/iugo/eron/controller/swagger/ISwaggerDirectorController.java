package com.iugo.eron.controller.swagger;

import java.util.concurrent.ExecutionException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ISwaggerDirectorController {

	@Operation(
		summary = "Fetch directors of movies by threshold", 
		description = "Directors whose number of movies directed is strictly greater than the given threshold"
	)
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "Successful operation",
				content = @Content(
					examples = { @ExampleObject(
						value = "{'directors': ['M. Night Shyamalan', 'Martin Scorsese', 'Woody Allen']}"
					)},
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				array = @ArraySchema(schema = @Schema(implementation = String.class)))),
			@ApiResponse(responseCode = "400", description = "Bad request. Invalid threshold", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content), 
	})
	ResponseEntity<?> getDirectors(@RequestParam(value="threshold", required = true) int threshold ) throws InterruptedException, ExecutionException;
}
