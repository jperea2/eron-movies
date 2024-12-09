package com.iugo.eron.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class Movie {

	private String title;	
	private Integer year;	
	private String rated;
	private String released;
	private String runtime;
	private String genre;
	private String director;
	private String witer;
	private String actors;
    
}
