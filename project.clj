(defproject cleverly-crm "0.1.0-SNAPSHOT"
  :description "Wedding Software"
  :url "https://github.com/DavidLapes/wedding"
  :license {:name "Apache License 2.0"
            :url "https://github.com/DavidLapes/wedding/blob/master/LICENSE"}

  :dependencies [[org.clojure/clojure "1.10.3"]]

  :source-paths ["src"]
  :resource-paths ["resources"]

  :repl-options {:init-ns wedding.core}

  :pedantic? :abort

  :main wedding.core

  :profiles {:uberjar {:aot [wedding.core]
                       :main wedding.core}
             :dev {:dependencies [[nrepl "0.8.3"]]}})
