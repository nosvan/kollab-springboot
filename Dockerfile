FROM openjdk:18 as builder
WORKDIR .
COPY . .
RUN ./mvnw clean package

FROM openjdk:18
WORKDIR .
EXPOSE 8080
COPY --from=builder /target/kollab-0.0.1.jar .
CMD java -jar kollab-0.0.1.jar