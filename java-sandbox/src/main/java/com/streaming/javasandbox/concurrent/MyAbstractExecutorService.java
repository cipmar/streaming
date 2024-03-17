package com.streaming.javasandbox.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class MyAbstractExecutorService implements ExecutorService {
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return null;
    }

    private <T> T doInvokeAny(Collection<? extends Callable<T>> tasks, boolean timed, long nanos) {

        if ( tasks == null) {
            throw new NullPointerException();
        }

        int ntasks = tasks.size();
        if (ntasks == 0) {
            throw new IllegalArgumentException();
        }

        ArrayList<Future<T>> futures = new ArrayList<>(ntasks);

        return null;
    }
}
