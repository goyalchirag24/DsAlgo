package com.dsalgo;

import java.lang.reflect.Array;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SegmentTree<T, E> {
	private final T[] inputArray;
	private final E[] segmentTree;
	private final BiFunction<E, E, E> operatorFunc;
	private final Function<T, E> outputFunc;

	public SegmentTree(T[] array, BiFunction<E, E, E> function, Class<E> outputClass, Function<T, E> outputFunction) {
		if (array == null || array.length == 0)
			throw new IllegalArgumentException("array can not be null");
		this.inputArray = array;
		segmentTree = (E[]) Array.newInstance(outputClass, array.length * 4);
		this.operatorFunc = function;
		this.outputFunc = outputFunction;
		build(0, inputArray.length - 1, 0);
	}

	private void build(int left, int right, int currentPos) {
		if (left == right) {
			segmentTree[currentPos] = outputFunc.apply(inputArray[left]);
			return;
		}
		int mid = (left + right) / 2;
		build(left, mid, currentPos * 2 + 1);
		build(mid + 1, right, currentPos * 2 + 2);
		segmentTree[currentPos] = operatorFunc.apply(segmentTree[currentPos * 2 + 1], segmentTree[currentPos * 2 + 2]);
	}

	public E rangeSum(int start, int end) {
		if (start < 0 || end >= inputArray.length)
			throw new IllegalArgumentException("Range is not valid");
		return rangeSum(start, end, 0, inputArray.length - 1, 0);
	}

	private E rangeSum(int qs, int qe, int rs, int re, int currentPos) {
		if (qs <= rs && re <= qe) {
			return segmentTree[currentPos];
		}
		if (qs > re || qe < rs) {
			return null;
		}
		int mid = (rs + re) / 2;
		E left = rangeSum(qs, qe, rs, mid, currentPos * 2 + 1);
		E right = rangeSum(qs, qe, mid + 1, re, currentPos * 2 + 2);
		if (left == null)
			return right;
		else if (right == null)
			return left;
		return operatorFunc.apply(left, right);
	}

	public void update(int index, T value) {
		if (index < 0 || index >= inputArray.length)
			throw new IllegalArgumentException("index is not valid");
		update(index, value, 0, inputArray.length - 1, 0);
	}

	private void update(int index, T value, int left, int right, int currPos) {
		if (left == right) {
			segmentTree[currPos] = outputFunc.apply(value);
			return;
		}
		int mid = (left + right) / 2;
		if (index <= mid) {
			update(index, value, left, mid, currPos * 2 + 1);
		} else {
			update(index, value, mid + 1, right, currPos * 2 + 2);
		}
		segmentTree[currPos] = operatorFunc.apply(segmentTree[currPos * 2 + 1], segmentTree[currPos * 2 + 2]);
	}
}
