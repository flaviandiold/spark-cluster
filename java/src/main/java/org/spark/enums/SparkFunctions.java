package org.spark.enums;

public enum SparkFunctions {
	FILTER,
	MAP_REDUCE;

	public static SparkFunctions fromString(String func) {
		for (SparkFunctions function : SparkFunctions.values()) {
			if (function.name().equalsIgnoreCase(func)) {
				return function;
			}
		}
		return SparkFunctions.FILTER;
	}
}
