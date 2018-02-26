FROM java:8
COPY wap-tools-0.0.1-SNAPSHOT.jar /app/run.jar
WORKDIR /app
EXPOSE 8089
CMD java -Dspring.config.location=file:/config/application.properties -jar run.jar
