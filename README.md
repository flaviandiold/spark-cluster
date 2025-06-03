# Initialize project

1. `cd java`
2. `mvn clean package`
3. `cd ../spark-cluster`
4. `docker compose up --build -d`
5. Run any of the below shown commands in your terminal

# Filter Functionality in Spark

Run command
```
docker exec spark-master spark-submit \
  --class org.spark.SparkClient \
  --master spark://spark-master:7077 \
  /app/target/spark-java-client-1.0-SNAPSHOT.jar FILTER
```

# MapReduce in Spark

Run command
```
docker exec spark-master spark-submit \
  --class org.spark.SparkClient \
  --master spark://spark-master:7077 \
  /app/target/spark-java-client-1.0-SNAPSHOT.jar MAP_REDUCE
```


## Note:
Since the input dataset is very small in size, Spark will decide to only work with one worker. 
Try this command to generate a very large story.txt (500MB)
`yes "$(cat story.txt)" | head -c 524288000 > big_story.txt`

Works for Mac and Linux
