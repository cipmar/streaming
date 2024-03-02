package com.streaming.javasandbox.hof;

import java.util.function.IntBinaryOperator;

public class SimpleExample {

	public static int applyBinaryOperation(int a, int b, IntBinaryOperator operator) {
		return operator.applyAsInt(a, b);
	}

	public static void main(String[] args) {

		IntBinaryOperator add = (a, b) -> a + b;
		IntBinaryOperator multiply = (a, b) -> a * b;
		IntBinaryOperator subtract = (a, b) -> a - b;

		int x = 19;
		int y = 33;

		int resultAdd = applyBinaryOperation(x, y, add);
		int resultAdd2 = applyBinaryOperation(x, y, (a, b) -> a + b);
		int resultMultiply = applyBinaryOperation(x, y, multiply);
		int resultSubtract = applyBinaryOperation(x, y, subtract);

		System.out.println("Addition: " + resultAdd);
		System.out.println("Addition: " + resultAdd2);
		System.out.println("Multiplication: " + resultMultiply);
		System.out.println("Subtraction: " + resultSubtract);
	}
}
