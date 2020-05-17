package com.dsalgo;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Main {

	public static void main(String[] args) {
		BiFunction<Integer, Integer, Integer> biFunction = Integer::sum;
		int[] a = new int[] { 1, 0, -3, 6, 8, 12, -5, 7 };
		Integer[] arr = IntStream.of(a).boxed().toArray(Integer[]::new);
		SegmentTree<Integer, Integer> segmentTree = new SegmentTree<Integer, Integer>(arr, biFunction, Integer.class,
				Function.identity());
		System.out.println(segmentTree.rangeSum(0, 7));
		segmentTree.update(4, 10);
		System.out.println(segmentTree.rangeSum(0, 7));
	}
}
