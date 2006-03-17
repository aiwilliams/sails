package org.opensails.sails.tester.util;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;
import junit.framework.ComparisonFailure;

public class CollectionAssert {

	@SuppressWarnings("unchecked")
	public static void containsOnly(Collection expected, Collection actual) {
		Assert.assertEquals("Actual should contain expected number of items.", expected.size(), actual.size());
		if (!actual.containsAll(expected)) throw new ComparisonFailure("Expected to contain all in no particular order", expected.toString(), actual.toString());
	}

	public static void containsOnly(Collection expected, Object[] actual) {
		containsOnly(expected, Arrays.asList(actual));
	}

	public static void containsOnly(Object expected, Collection actual) {
		Assert.assertEquals("Actual should contain expected number of items.", 1, actual.size());
		if (!actual.contains(expected)) throw new ComparisonFailure("Expected to contain all in no particular order", expected.toString(), actual.toString());
	}

	public static void containsOnly(Object expected, Object[] actual) {
		Assert.assertEquals("Actual should contain expected number of items.", 1, actual.length);
		if (!actual[0].equals(expected)) throw new ComparisonFailure("Expected to contain all in no particular order", expected.toString(), actual.toString());
	}

	@SuppressWarnings("unchecked")
	public static void containsOnly(Object[] expected, Collection actual) {
		Assert.assertEquals("Actual should contain expected number of items.", expected.length, actual.size());
		if (!actual.containsAll(Arrays.asList(expected))) throw new ComparisonFailure("Expected to contain all in no particular order", Arrays.toString(expected), actual.toString());
	}

	public static void containsOnly(Object[] expected, Object[] actual) {
		Assert.assertEquals("Actual should contain expected number of items.", expected.length, actual.length);
		if (!Arrays.asList(actual).containsAll(Arrays.asList(expected))) throw new ComparisonFailure("Expected to contain all in no particular order", expected.toString(), actual.toString());
	}

	public static void containsOnlyOrdered(Collection expected, Collection actual) {
		Assert.assertEquals("Actual should contain expected number of items.", expected.size(), actual.size());
		Assert.assertTrue("Expected to contain all in exact order", expected.equals(actual));
	}

	public static void containsOnlyOrdered(Object[] expected, Collection actual) {
		containsOnlyOrdered(Arrays.asList(expected), actual);
	}

	public static void containsOnlyOrdered(Object[] expected, Object[] actual) {
		containsOnlyOrdered(Arrays.asList(expected), Arrays.asList(actual));
	}
}