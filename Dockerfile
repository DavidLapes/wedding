FROM openjdk:8u181-alpine3.8
WORKDIR /
COPY target/wedding-0.1.0-SNAPSHOT-standalone.jar wedding.jar
EXPOSE 5000

CMD java -jar wedding.jar
