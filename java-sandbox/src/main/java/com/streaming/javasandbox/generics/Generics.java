package com.streaming.javasandbox.generics;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

public class Generics {
	public static <T extends Comparable<T>> int search(List<T> list, T element) {
		var lo = 0;
		var hi = list.size() - 1;

		while (lo <= hi) {
			var mid = (lo + hi) / 2;
			var cmp = list.get(mid).compareTo(element);

			if (cmp == 0) {
				return mid;
			} else if (cmp > 0) {
				hi = mid - 1;
			} else {
				lo = mid + 1;
			}
		}

		return -1;
	}

	public static <T extends Comparable<T>> void genericSort(List<T> list) {
		for (var i = 0; i < list.size(); i++) {
			for (var j = 0; j < list.size(); j++) {
				if (list.get(i).compareTo(list.get(j)) < 0) {
					var tmp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, tmp);
				}
			}
		}
	}

	public static <T extends Comparable<T>> Optional<T> max(List<T> list) {
		return genericMinMax(list, (x, y) -> x.compareTo(y) < 0);
	}

	public static <T extends Comparable<T>> Optional<T> min(List<T> list) {
		return genericMinMax(list, (x, y) -> x.compareTo(y) > 0);
	}

	public static boolean findSum(List<Integer> list, int sum) {
		var lo = 0;
		var hi = list.size() - 1;

		while (lo < hi) {
			var tmp = list.get(lo) + list.get(hi);

			if (tmp == sum) {
				return true;
			} else if (tmp < sum) {
				lo++;
			} else {
				hi--;
			}
		}
		return false;
	}

	private static <T extends Comparable<T>> Optional<T> genericMinMax(List<T> list, BiPredicate<T, T> predicate) {
		Optional<T> max = Optional.empty();

		for (var x : list) {
			if (max.isEmpty()) {
				max = Optional.of(x);
			} else {
				if (predicate.test(max.get(), x)) {
					max = Optional.of(x);
				}
			}
		}
		return max;
	}
}
