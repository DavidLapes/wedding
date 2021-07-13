FROM openjdk:8u181-alpine3.8
WORKDIR /
EXPOSE 5000

CMD java -jar wedding.jar
