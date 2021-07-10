(defproject wedding "0.1.0-SNAPSHOT"
  :description "Wedding Software"
  :url "https://github.com/DavidLapes/wedding"
  :license {:name "Apache License 2.0"
            :url "https://github.com/DavidLapes/wedding/blob/master/LICENSE"}

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [http-kit "2.3.0"]
                 [metosin/reitit "0.5.13" :exclusions [org.clojure/tools.reader
                                                       com.fasterxml.jackson.datatype/jackson-datatype-jsr310
                                                       metosin/jsonista
                                                       prismatic/schema]]
                 [metosin/reitit-ring "0.5.13"]
                 [metosin/reitit-spec "0.5.13"]
                 [metosin/reitit-swagger "0.5.13"]
                 [metosin/reitit-swagger-ui "0.5.13"]
                 [metosin/ring-http-response "0.9.2"]
                 [ring-logger "1.0.1"]
                 [environ "1.2.0"]
                 [org.slf4j/slf4j-api "1.7.30"]
                 [com.taoensso/timbre "4.10.0"]
                 [com.fzakaria/slf4j-timbre "0.3.21"]
                 [org.slf4j/log4j-over-slf4j "1.7.30"]
                 [org.slf4j/jul-to-slf4j "1.7.30"]
                 [org.slf4j/jcl-over-slf4j "1.7.30"]
                 [com.stuartsierra/component "1.0.0"]
                 [hikari-cp "2.12.0"]
                 [clj-postgresql "0.7.0" :exclusions [ch.qos.logback/logback-core
                                                      ch.qos.logback/logback-classic]]]

  :plugins [[lein-environ "1.2.0" :hooks false]]

  :source-paths ["src"]
  :resource-paths ["resources"]

  :repl-options {:init-ns dev-user}

  :pedantic? :abort

  :main wedding.core

  :profiles {:uberjar {:aot [wedding.core]
                       :main wedding.core}
             :dev {:dependencies [[nrepl "0.8.3"]]
                   :source-paths   ["dev"]
                   :resource-paths ["dev/resources"]}})
