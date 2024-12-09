package com.iugo.eron.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.iugo.eron.dto.CustomPageImpl;

public interface IPageService<T> {

	static final int FIRST_PAGE = 1;
	
	CompletableFuture<CustomPageImpl<T>> getByNumberPage(int numberPage);
	
	default CompletableFuture<List<CustomPageImpl<T>>> allOf(List<CompletableFuture<CustomPageImpl<T>>> futuresList) {
		return CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0]))
	            .thenApply(v -> futuresList.stream()
	                    .map(CompletableFuture::join)
	                    .collect(Collectors.toList()));
	}
	
	default CompletableFuture<List<CustomPageImpl<T>>> getAllPages() {		
	    return getByNumberPage(FIRST_PAGE).thenCompose(firstPage -> {	    	
	        List<CompletableFuture<CustomPageImpl<T>>> futuresList = 
	        	IntStream.rangeClosed(FIRST_PAGE+1, firstPage.getTotalPages())
	        		.mapToObj(this::getByNumberPage)            
	        		.collect(Collectors.toList());
	        futuresList.add(CompletableFuture.completedFuture(firstPage));
	        return allOf(futuresList);
	    });
	}
}