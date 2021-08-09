(ns microservice.component.aws-creds-provider
  (:require [com.stuartsierra.component :as component]
            [taoensso.timbre :as timbre]))

(def ^:private credentials
  {:access-key  "AKIASN2FZSDE5UYORYZ2"
   :secret-key  "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"
   :region      :eu-west-1})

(defrecord AWSCredentialsProvider []
  component/Lifecycle
  (start [this]
    (timbre/info "Starting AWSCredentialsProvider")
    (timbre/info "Started AWSCredentialsProvider")
    (assoc this :provider {:credentials credentials}))

  (stop [this]
    (timbre/info "Stopping AWSCredentialsProvider")
    (timbre/info "Stopped AWSCredentialsProvider")
    (dissoc this :provider)))

(defn new-credentials-provider
  "Returns instance of AWSCredentialsProvider component."
  []
  (map->AWSCredentialsProvider {}))
