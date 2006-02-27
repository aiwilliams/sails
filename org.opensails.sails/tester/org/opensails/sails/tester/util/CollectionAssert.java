package org.opensails.sails.tester.util;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;
import junit.framework.ComparisonFailure;

public class CollectionAssert {

	public static <T> void containsOnly(Collection<T> expected, T[] actual) {
		containsOnly(expected, Arrays.asList(actual));
	}

	public static <T> void containsOnly(T expected, Collection<T> actual) {
		Assert.assertEquals("Actual should contain expected number of items.", 1, actual.size());
		if (!actual.contains(expected)) throw new ComparisonFailure("Expected to contain all in no particular order", expected.toString(), actual.toString());
	}

	/*
	 * This method is a Generics HACK. The Eclipse compiler understands
	 * everything fine, but the Sun compiler don't.
	 */
	@SuppressWarnings("unchecked")
	public static void containsOnly(Collection expected, Collection actual) {
		Assert.assertEquals("Actual should contain expected number of items.", expected.size(), actual.size());
		if (!actual.containsAll(expected)) throw new ComparisonFailure("Expected to contain all in no particular order", expected.toString(), actual.toString());
	}

	public static <T> void containsOnly(T expected, T[] actual) {
		Assert.assertEquals("Actual should contain expected number of items.", 1, actual.length);
		if (!actual[0].equals(expected)) throw new ComparisonFailure("Expected to contain all in no particular order", expected.toString(), actual.toString());
	}

	public static <T> void containsOnly(T[] expected, Collection<T> actual) {
		Assert.assertEquals("Actual should contain expected number of items.", expected.length, actual.size());
		if (!actual.containsAll(Arrays.asList(expected))) throw new ComparisonFailure("Expected to contain all in no particular order", Arrays.toString(expected), actual.toString());
	}

	public static <T> void containsOnly(T[] expected, T[] actual) {
		Assert.assertEquals("Actual should contain expected number of items.", expected.length, actual.length);
		if (!Arrays.asList(actual).containsAll(Arrays.asList(expected))) throw new ComparisonFailure("Expected to contain all in no particular order", expected.toString(), actual.toString());
	}

	public static <T> void containsOnlyOrdered(Collection<T> expected, Collection<T> actual) {
		Assert.assertEquals("Actual should contain expected number of items.", expected.size(), actual.size());
		Assert.assertTrue("Expected to contain all in exact order", expected.equals(actual));
	}

	public static <T> void containsOnlyOrdered(T[] expected, Collection<T> actual) {
		containsOnlyOrdered(Arrays.asList(expected), actual);
	}

	public static <T> void containsOnlyOrdered(T[] expected, T[] actual) {
		containsOnlyOrdered(Arrays.asList(expected), Arrays.asList(actual));
	}
}