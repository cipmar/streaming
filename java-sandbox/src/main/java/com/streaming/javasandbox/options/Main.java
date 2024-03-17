package com.streaming.javasandbox.options;

public class Main {

	public static void main(String[] args) {

		System.getenv().forEach((key, value) -> {
			System.out.println(key + ": " + value);
		});

		System.getProperties().forEach((key, value) -> {
			System.out.println(key + ": " + value);
		});

		System.out.println(System.getProperty("sun.io.useCanonCaches"));
		System.out.println(System.getProperty("sun.io.useCanonPrefixCache"));
	}
}
