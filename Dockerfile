FROM clojure:lein
WORKDIR '/app'
COPY project.clj ./
COPY src ./src
RUN lein uberjar
EXPOSE 5000

CMD java -jar ./target/wedding-0.1.0-SNAPSHOT-standalone.jar
