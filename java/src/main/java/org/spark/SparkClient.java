package org.spark;

import org.apache.spark.sql.SparkSession;
import org.spark.enums.SparkFunctions;
import org.spark.functions.Filter;
import org.spark.functions.MapReduce;
import org.spark.functions.SparkFunctionsInterface;
import org.spark.utils.LoggerUtils;

public class SparkClient {
    public static void main(String[] args) {
        String functionArg = args.length > 0 ? args[0] : SparkFunctions.FILTER.name();
        SparkFunctions function = SparkFunctions.fromString(functionArg);

        LoggerUtils.log(String.format("RUNNING %s", functionArg));
        try (SparkSession session = SparkSession.builder()
                .appName("Spark Client")
                .master("spark://spark-master:7077")
                .getOrCreate()) {

            SparkFunctionsInterface sparkFunction = switch (function) {
                case FILTER ->
                    new Filter(session);
                case MAP_REDUCE ->
                    new MapReduce(session);
                default ->
                    throw new IllegalArgumentException("Unsupported function: " + function);
            };

            sparkFunction.spark();
        }
    }
}
