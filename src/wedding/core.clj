(ns wedding.core
  (:gen-class)
  (:require [com.stuartsierra.component :refer [start]]
            [microservice.system :as system]
            [taoensso.timbre :as timbre]))

(defn -main
  "Starts Wedding application."
  [& args]
  (timbre/info "Starting Wedding application")
  ;;TODO: Remove
  (doseq [x environ.core/env]
    (println (str x ": - " (get environ.core/env x))))
  (start (system/make-system))
  (timbre/info "Started Wedding application")
  :ok)
