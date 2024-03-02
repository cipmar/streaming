package com.streaming.javasandbox.hof;

import java.util.function.Function;

public class FunctionComposition {

	public static void main(String[] args) {

		Function<Integer, Integer> addTwo = x -> x + 2;
		Function<Integer, Integer> multiplyByThree = x -> x * 3;
		Function<Integer, Integer> addAndThenMultiply = addTwo.andThen(multiplyByThree);

		int result = addAndThenMultiply.apply(10);
		System.out.println("Result andThen: " + result);

		Function<Integer, Integer> addComposeMultiply = addTwo.compose(multiplyByThree);

		int result2 = addComposeMultiply.apply(10);
		System.out.println("Result compose: " + result2);
	}
}
