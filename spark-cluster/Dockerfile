FROM bitnami/spark:latest

WORKDIR /app

USER root

COPY spark-defaults.conf /opt/bitnami/spark/conf/spark-defaults.conf

RUN echo 'sparkuser:x:1001:0:sparkuser:/home/sparkuser:/bin/bash' >> /etc/passwd

USER sparkuser