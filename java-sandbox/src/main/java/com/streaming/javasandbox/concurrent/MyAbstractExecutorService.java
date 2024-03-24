package com.streaming.javasandbox.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class MyAbstractExecutorService implements ExecutorService {
	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		try {
			return doInvokeAny(tasks, false, 0);
		} catch (TimeoutException cannotHappen) {
			assert false;
			return null;
		}
	}

	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return doInvokeAny(tasks, true, unit.toNanos(timeout));
	}

	private <T> T doInvokeAny(Collection<? extends Callable<T>> tasks, boolean timed, long nanos) throws TimeoutException {

		if (tasks == null) {
			throw new NullPointerException();
		}

		int ntasks = tasks.size();
		if (ntasks == 0) {
			throw new IllegalArgumentException();
		}

		ArrayList<Future<T>> futures = new ArrayList<>(ntasks);
		MyExecutorCompletionService<T> ecs = new MyExecutorCompletionService<>(this);

		// todo ...
		return null;
	}
}
