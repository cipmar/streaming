package com.streaming.javasandbox.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface MyExecutorService extends MyExecutor {
	void shutdown();

	List<Runnable> shutdownNow();

	boolean isShutdown();

	boolean isTerminated();

	boolean awaitTermination(long timeout, TimeUnit unit)
			throws InterruptedException;

	<T> Future<T> submit(Callable<T> task);

	<T> Future<T> submit(Runnable task, T result);

	<T> Future<T> submit(Runnable task);

	<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException;

	<T> T invokeAny(Collection<? extends Callable<T>> tasks)
			throws InterruptedException, ExecutionException;

	<T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException;
}
