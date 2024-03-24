package com.streaming.javasandbox.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface MyCompletionService<V> {

	Future<V> submit(Callable<V> task);

	Future<V> submit(Runnable task, V result);

	Future<V> take() throws InterruptedException;

	Future<V> pool();

	Future<V> pool(long timeout, TimeUnit unit) throws InterruptedException;
}
