(ns wedding.core
  (:gen-class)
  (:require [microservice.system :as system]
            [com.stuartsierra.component :refer [start]]
            [taoensso.timbre :as timbre]))

(defn -main
  "Starts Wedding application."
  [& args]
  (timbre/info "Starting Wedding application")
  (start (system/make-system))
  (timbre/info "Started Wedding application")
  :ok)
