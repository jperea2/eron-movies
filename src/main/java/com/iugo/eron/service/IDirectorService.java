package com.iugo.eron.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IDirectorService<T> extends IPageService<T> {

	List<String> getDirectors(int threshold) throws InterruptedException, ExecutionException;

}