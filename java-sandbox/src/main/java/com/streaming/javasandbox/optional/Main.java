package com.streaming.javasandbox.optional;

import java.util.Objects;

public class Main {
	public static void main(String[] args) {
		MyOptional<String> optional1 = MyOptional.of("hello");
		String s1 = optional1.orEleseGet(() -> "ccc");
		System.out.println(s1);
		System.out.println(Objects.equals(s1, "hello"));

		MyOptional<String> optional2 = MyOptional.empty();
		String s2 = optional2.orEleseGet(() -> "ccc");
		System.out.println(s2);
		System.out.println(Objects.equals(s2, "ccc"));
	}
}
