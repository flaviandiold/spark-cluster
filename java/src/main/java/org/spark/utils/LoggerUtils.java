package org.spark.utils;

import org.apache.log4j.Logger;
import org.spark.SparkClient;

public class LoggerUtils {
    private static final Logger logger = Logger.getLogger(SparkClient.class);

    public static void log(String message) {
        // To find logs in `worker` nodes go to /opt/bitnami/spark/work/<job-id>/<task-id>/stderr in worker node container
        logger.info(">".repeat(20) + String.format(" [%s]: ", System.getenv("NODE_ID")) + message);
    }
}