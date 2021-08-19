(ns microservice.component.email-notification-adapter
  (:require [com.stuartsierra.component :as component]
            [microservice.component.proto.notification :as proto]
            [ses-mailer.core :as ses-mailer]
            [taoensso.timbre :as timbre]))

(def ^:private sender "info@terkaberedavida.cz")

(defrecord EmailNotificationAdapter [aws-credentials-provider]
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
  (-notify [{:keys [recipient subject text] :as data}]
    (timbre/info (str "Preparing notification for: " recipient))
    (let [creds (-> aws-credentials-provider :provider :credentials)]
      (ses-mailer/send-email
        creds
        sender
        recipient
        subject
        {:text-body text}))
    (timbre/info "Notified!")))

(defn new-email-notification-adapter
  "Returns instance of EmailNotificationAdapter component."
  [aws-credentials-provider-ref]
  (component/using
    (map->EmailNotificationAdapter {})
    {:aws-credentials-provider aws-credentials-provider-ref}))
