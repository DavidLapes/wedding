(ns wedding.core
  (:gen-class)
  (:require [com.stuartsierra.component :refer [start]]
            [microservice.system :as system]
            [taoensso.timbre :as timbre]))

(defn -main
  "Starts Wedding application."
  [& args]
  (timbre/info "Starting Wedding application")
  (start (system/make-system))
  (timbre/info "Started Wedding application")
  :ok)
