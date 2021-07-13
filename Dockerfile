FROM openjdk:8u181-alpine3.8
WORKDIR /
COPY wedding.jar ./
EXPOSE 5000

CMD java -jar wedding.jar
