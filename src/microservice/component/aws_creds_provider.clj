(ns microservice.component.aws-creds-provider
  (:require [com.stuartsierra.component :as component]
            [taoensso.timbre :as timbre]
            [wedding.lib.env :refer [get-env]]))

(defn- credentials []
  {:access-key  (get-env :aws-access-key)
   :secret-key  (get-env :aws-secret-key)
   :region      (get-env :aws-region)})

(defrecord AWSCredentialsProvider []
  component/Lifecycle
  (start [this]
    (timbre/info "Starting AWSCredentialsProvider")
    (timbre/info "Started AWSCredentialsProvider")
    (assoc this :provider {:credentials (credentials)}))

  (stop [this]
    (timbre/info "Stopping AWSCredentialsProvider")
    (timbre/info "Stopped AWSCredentialsProvider")
    (dissoc this :provider)))

(defn new-credentials-provider
  "Returns instance of AWSCredentialsProvider component."
  []
  (map->AWSCredentialsProvider {}))
