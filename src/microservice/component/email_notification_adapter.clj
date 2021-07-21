(ns microservice.component.email-notification-adapter
  (:require [com.stuartsierra.component :as component]
            [microservice.component.proto.notification :as proto]
            [taoensso.timbre :as timbre]))

(defrecord EmailNotificationAdapter []
  component/Lifecycle
  (start [this]
    (timbre/info "Starting EmailNotificationAdapter")
    (timbre/info "Started EmailNotificationAdapter")
    (assoc this :email-notification-adapter "Hello, world!"))

  (stop [this]
    (timbre/info "Stopping EmailNotificationAdapter")
    (timbre/info "Stopped EmailNotificationAdapter")
    (dissoc this :email-notification-adapter))

  proto/SNSNotificationAdapter
  (-notify [data]
    (timbre/info "Notified!")))
