(ns wedding.core
  (:gen-class)
  (:require [microservice.system :as system]
            [com.stuartsierra.component :refer [start]]
            [taoensso.timbre :as timbre]))

(defn -main
  "Starts Wedding application."
  [& args]
  (timbre/info "Starting Wedding application")
  ;;TODO: Remove
  (Thread/setDefaultUncaughtExceptionHandler
    (reify Thread$UncaughtExceptionHandler
      (uncaughtException [_ thread ex]
        (timbre/error ex "Uncaught exception on" (.getName thread)))))
  (start (system/make-system))
  (timbre/info "Started Wedding application")
  :ok)
