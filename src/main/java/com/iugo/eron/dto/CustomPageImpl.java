package com.iugo.eron.dto;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomPageImpl<T> extends PageImpl<T> {

	private static final long serialVersionUID = 1L;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    
    public CustomPageImpl( @JsonProperty("page") int number,
		@JsonProperty("per_page") int size,
		@JsonProperty("total") Long totalElements,
		@JsonProperty("total_pages") int totalPages,
		@JsonProperty("data") List<T> content){
			super(content, PageRequest.of(number-1, size), totalElements);
   }
}
