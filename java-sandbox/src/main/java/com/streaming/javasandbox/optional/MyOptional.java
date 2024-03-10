package com.streaming.javasandbox.optional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class MyOptional<T> {
	private static final MyOptional<?> EMPTY = new MyOptional<>(null);

	private final T value;

	public MyOptional(T value) {
		this.value = value;
	}

	public static <T> MyOptional<T> empty() {
		@SuppressWarnings("unchecked") MyOptional<T> t = (MyOptional<T>) EMPTY;
		return t;
	}

	public static <T> MyOptional<T> of(T value) {
		return new MyOptional<>(Objects.requireNonNull(value));
	}

	public static <T> MyOptional<T> ofNullable(T value) {
		return value != null ? new MyOptional<>(value) : (MyOptional<T>) EMPTY;
	}

	public T get() {
		if (value != null) {
			return value;
		} else {
			throw new NoSuchElementException("No value present");
		}
	}

	public MyOptional<T> filter(Predicate<? super T> predicate) {
		Objects.requireNonNull(predicate);

		if (!isPresent()) {
			return this;
		} else {
			return predicate.test(value) ? this : empty();
		}
	}

	public <U> MyOptional<U> map(Function<? super T, ? extends U> mapper) {
		Objects.requireNonNull(mapper);

		if (!isPresent()) {
			return empty();
		} else {
			return ofNullable(mapper.apply(value));
		}
	}

	public <U> MyOptional<U> flatMap(Function<? super T, MyOptional<? extends U>> mapper) {
		Objects.requireNonNull(mapper);

		if (!isPresent()) {
			return empty();
		} else {
			MyOptional<U> apply = (MyOptional<U>) mapper.apply(value);
			return Objects.requireNonNull(apply);
		}
	}

	public MyOptional<T> or(Supplier<? extends MyOptional<? extends T>> supplier) {
		Objects.requireNonNull(supplier);

		if (!isPresent()) {
			return empty();
		} else {
			MyOptional<T> myOptional = (MyOptional<T>) supplier.get();
			return Objects.requireNonNull(myOptional);
		}
	}

	public Stream<T> stream() {
		return !isPresent() ? Stream.empty() : Stream.of(value);
	}

	public T ofElse(T other) {
		return value != null ? value : other;
	}

	public T orEleseGet(Supplier<? extends T> supplier) {
		return value != null ? value : supplier.get();
	}

	public T orElseThrow() {
		if (value == null) {
			throw new NoSuchElementException("No value present");
		} else {
			return value;
		}
	}

	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
		if (value != null) {
			return value;
		} else {
			throw exceptionSupplier.get();
		}
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		return obj instanceof MyOptional<?> other && Objects.equals(value, other.value);
	}

	public int hadCode() {
		return Objects.hashCode(value);
	}

	public String toString() {
		return value != null ? ("MyOptional[" + value + "]") : "MyOptional.empty";
	}

	public boolean isPresent() {
		return value != null;
	}

	public boolean isEmpty() {
		return value == null;
	}

	public void ifPresent(Consumer<? super T> action) {
		if (value != null) {
			action.accept(value);
		}
	}

	public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
		if (value != null) {
			action.accept(value);
		} else {
			emptyAction.run();
		}
	}
}
