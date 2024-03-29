(ns microservice.component.web-server
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [org.httpkit.server :as server]
            [taoensso.timbre :as timbre]))

(defrecord WebServer [handler port thread-count]
  component/Lifecycle

  (start [this]
    (timbre/info "Starting WebServer component")
    (let [handler (:handler handler)
          server (server/run-server handler {:port   port
                                             :thread thread-count})]
      (timbre/info "Started WebServer component")
      (timbre/info (str "SERVER PORT - { " port " }"))
      (assoc this :web-server server)))

  (stop [this]
    (timbre/info "Stopping WebServer component")
    (let [server (:web-server this)]
      (server)
      (timbre/info "Stopped WebServer component")
      (dissoc this :web-server))))

(defn new-webserver
  "Returns instance of WebServer component."
  [handler-ref]
  (component/using
    (map->WebServer {:port         (get env :wedding-web-server-port 5000)
                     :thread-count (get env :wedding-web-server-thread-count 8)})
    {:handler handler-ref}))
