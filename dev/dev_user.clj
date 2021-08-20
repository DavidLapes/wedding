(ns dev-user
  (:require [com.stuartsierra.component.repl :as repl]
            [microservice.system :refer [make-system]]
            [taoensso.timbre :as timbre])
  (:import (clojure.lang IDeref)))

(repl/set-init
  (fn [& args]
    (make-system)))

(def ^:private system
  ^{:doc "Provides access to the current system components"}
  (reify IDeref
    (deref [this]
      repl/system)))

(defn reset
  "Reset the system and reload code changes"
  []
  (timbre/info "Re-starting Wedding application")
  (repl/reset)
  (timbre/info "Re-started Wedding application")
  :reset)

(defn start
  "Start the system"
  []
  (timbre/info "Starting Wedding application")
  (repl/start)
  (timbre/info "Started Wedding application")
  :start)

(defn stop
  "Stop the system"
  []
  (timbre/info "Stopping Wedding application")
  (repl/stop)
  (timbre/info "Stopped Wedding application")
  :stop)

(comment

  (def datasource (-> @system :wedding.component/datasource :datasource)))
