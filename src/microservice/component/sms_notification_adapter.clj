(ns microservice.component.sms_notification_adapter
  (:require [com.stuartsierra.component :as component]
            [microservice.component.proto.notification :as proto]
            [taoensso.timbre :as timbre]))

(defrecord SMSNotificationAdapter []
  component/Lifecycle
  (start [this]
    (timbre/info "Starting SMSNotificationAdapter")
    (timbre/info "Started SMSNotificationAdapter")
    (assoc this :sms-notification-adapter "Hello, world!"))

  (stop [this]
    (timbre/info "Stopping SMSNotificationAdapter")
    (timbre/info "Stopped SMSNotificationAdapter")
    (dissoc this :sms-notification-adapter))

  proto/SNSNotificationAdapter
  (-notify [data]
    (timbre/info "Notified!")))
