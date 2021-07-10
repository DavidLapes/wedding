(ns dev-user
  (:require [com.stuartsierra.component :as component]
            [microservice.system :as system]
            [taoensso.timbre :as timbre]))

(def ^:private ^:dynamic
  ^{:doc "Contains current component system state for dev purposes"}
  system nil)

(def ^:private
  ^{:doc "Starts the component system"}
  start-system #'component/start)

(def ^:private
  ^{:doc "Stops the component system"}
  stop-system #'component/stop)

(defn- start
  []
  "Starts Wedding application."
  (when-not system
    (timbre/info "Starting Wedding application")
    (let [new-system (start-system (system/make-system))]
      (alter-var-root #'system (constantly new-system))
      (timbre/info "Started Wedding application")
      :ok)))

(defn- stop
  "Stops Wedding application."
  []
  (when system
    (timbre/info "Stopping Wedding application")
    (stop-system system)
    (alter-var-root #'system (constantly nil))
    (timbre/info "Stopped Wedding application")
    :stop))

(defn- reset
  []
  "Resets Wedding application."
  (timbre/info "Re-starting Wedding application")
  (stop)
  (start)
  (timbre/info "Re-started Wedding application")
  :reset)
