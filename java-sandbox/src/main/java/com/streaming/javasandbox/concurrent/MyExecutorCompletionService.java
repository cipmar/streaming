package com.streaming.javasandbox.concurrent;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

public class MyExecutorCompletionService<V> implements MyCompletionService<V> {
	private final Executor executor;
	private final AbstractExecutorService aes;
	private final BlockingQueue<Future<V>> completionQueue;

	private static class QueueingFuture<V> extends FutureTask<Void> {
		private final Future<V> task;
		private final BlockingQueue<Future<V>> completionQueue;

		public QueueingFuture(RunnableFuture<V> task, BlockingQueue<Future<V>> completionQueue) {
			super(task, null);
			this.task = task;
			this.completionQueue = completionQueue;
		}

		@Override
		protected void done() {
			completionQueue.add(task);
		}
	}

	public MyExecutorCompletionService(Executor executor) {
		if (executor == null) {
			throw new NullPointerException();
		}
		this.executor = executor;
		this.aes = (executor instanceof AbstractExecutorService) ? (AbstractExecutorService) executor : null;
		this.completionQueue = new LinkedBlockingQueue<>();
	}

	public MyExecutorCompletionService(Executor executor, BlockingQueue<Future<V>> completionQueue) {
		if (executor == null || completionQueue == null) {
			throw new NullPointerException();
		}
		this.executor = executor;
		this.aes = (executor instanceof AbstractExecutorService) ? (AbstractExecutorService) executor : null;
		this.completionQueue = completionQueue;
	}

	@Override
	public Future<V> submit(Callable<V> task) {
		if (task == null) throw new NullPointerException();
		RunnableFuture<V> f = newTaskFor(task);
		executor.execute(new QueueingFuture<V>(f, completionQueue));
		return f;
	}

	@Override
	public Future<V> submit(Runnable task, V result) {
		if (task == null) throw new NullPointerException();
		RunnableFuture<V> f = newTaskFor(task, result);
		executor.execute(new QueueingFuture<V>(f, completionQueue));
		return f;
	}

	@Override
	public Future<V> take() throws InterruptedException {
		return completionQueue.take();
	}

	@Override
	public Future<V> pool() {
		return completionQueue.poll();
	}

	@Override
	public Future<V> pool(long timeout, TimeUnit unit) throws InterruptedException {
		return completionQueue.poll(timeout, unit);
	}

	private RunnableFuture<V> newTaskFor(Callable<V> task) {
		return new FutureTask<V>(task);
	}

	private RunnableFuture<V> newTaskFor(Runnable task, V result) {
		return new FutureTask<>(task, result);
	}
}
