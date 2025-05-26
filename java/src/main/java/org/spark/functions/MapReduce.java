package org.spark.functions;

import java.util.Arrays;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.col;
import org.spark.utils.LoggerUtils;

public class MapReduce implements SparkFunctionsInterface {

    private final SparkSession spark;

    public MapReduce(SparkSession spark) {
        this.spark = spark;
    }

    @Override
    public void spark() {
        Dataset<String> lines = spark.read()
                .textFile("/app/dataset/inputs/story.txt");

        Dataset<Row> wordCounts = lines
                .flatMap(
                        (FlatMapFunction<String, String>) line -> {
                            LoggerUtils.log("Executor processing line: " + line);

                            // Split on whitespace, normalise, strip punctuation
                            return Arrays.stream(line.split("\\s+"))
                                    .map(word -> word.toLowerCase() // ignore case
                                    .replaceAll("^[\\p{Punct}]+|[\\p{Punct}]+$", "") // strip leading/trailing punctuation
                                    )
                                    .filter(word -> !word.isEmpty()) // skip blanks
                                    .iterator();
                        },
                        Encoders.STRING()
                )
                .groupBy(col("value").alias("word"))
                .count();

        wordCounts
                .coalesce(1) // Saves as a single file
                .write()
                .mode(SaveMode.Overwrite)
                .option("header", true) // includes column names
                .csv("/app/dataset/outputs/word_counts");
    }

}
