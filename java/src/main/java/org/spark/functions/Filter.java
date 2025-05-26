package org.spark.functions;

import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.col;
import org.spark.utils.LoggerUtils;

public class Filter implements SparkFunctionsInterface {

    private final SparkSession spark;

    public Filter(SparkSession spark) {
        this.spark = spark;
    }

    @Override
    public void spark() {
        Dataset<Row> df = spark.read()
                .option("header", true)
                .csv("/app/dataset/inputs/people.csv");

        df.foreach((ForeachFunction<Row>) (row) -> LoggerUtils.log(row.mkString()));
        Dataset<Row> filteredDf = df.filter(col("age").between(20, 25));
        filteredDf
                .coalesce(1) // Saves as a single file
                .write()
                .mode(SaveMode.Overwrite)
                .option("header", true)
                .csv("/app/dataset/outputs/people");
    }

}
