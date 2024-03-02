package com.streaming.javasandbox.hof;

import java.util.function.Function;

public class Curring {

	public static void main(String[] args) {

		Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;
		Function<Integer, Integer> add5 = add.apply(5);
		Integer result = add5.apply(7);

		System.out.println("Result: " + result);
	}
}
