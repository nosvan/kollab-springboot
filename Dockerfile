FROM openjdk:18
WORKDIR .
EXPOSE 8080
COPY /target/kollab-0.0.1.jar .
CMD java -jar kollab-0.0.1.jar