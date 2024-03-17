package com.streaming.javasandbox.generics;

import static com.streaming.javasandbox.generics.Generics.findSum;
import static com.streaming.javasandbox.generics.Generics.genericSort;
import static com.streaming.javasandbox.generics.Generics.max;
import static com.streaming.javasandbox.generics.Generics.min;
import static com.streaming.javasandbox.generics.Generics.search;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

public class Main {
	public static void main(final String[] args) throws Exception {
		var list1 = Arrays.asList(2, 5, 6, 7, 8, 10, 21, 34, 44, 59, 78, 99);
		var index = search(list1, 21);
		printList(list1, "list1");
		System.out.printf("The number %d in list1 is found at index %d%n", 21, index);

		var list = Arrays.asList(3, 4, 5, 2, 12, 10, 33, 12, 21, 22);
		genericSort(list);
		printList(list, "sorted list");

		var list3 = List.of(3, 4, 5, 2, 12, 10, 33, 12, 21, 22);
		printList(list3, "list3");

		var max = max(list3);
		System.out.printf("The max of list is %s%n", max);
		var min = min(list3);
		System.out.printf("The min of list is %s%n", min);

		var list4 = List.of(2, 4, 5, 6, 7, 8, 11, 13);
		printList(list4, "list4");
		var res = findSum(list4, 13);
		System.out.println(res);
	}

	private static <T> void printList(List<T> list, String label) {
		System.out.print(label + ": ");
		list.forEach(x -> System.out.print(x + " "));
		System.out.println();
	}

}
