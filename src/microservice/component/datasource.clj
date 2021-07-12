(ns microservice.component.datasource
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [hikari-cp.core :as hikari]
            [taoensso.timbre :as timbre]))

(def ^:private credentials
  {:username        (get env :WEDDING_DB_USER "postgres")
   :password        (get env :WEDDING_PASSWORD "password")
   :database-name   (get env :WEDDING_DB_NAME "wedding_db")
   :server-name     (get env :WEDDING_DB_HOST "localhost")
   :port-number     (get env :WEDDING_PORT 5432)})

(def ^:private additional-options
  {:auto-commit        true
   :read-only          false
   :connection-timeout 30000
   :validation-timeout 5000
   :idle-timeout       600000
   :max-lifetime       1800000
   :minimum-idle       10
   :maximum-pool-size  10
   :pool-name          "db-pool"
   :adapter            "postgresql"
   :register-mbeans    false})

(def ^:private datasource-schema
  (merge additional-options credentials))

(defrecord DataSource []
  component/Lifecycle

  (start [this]
    (timbre/info "Starting DataSource component")
    (let [datasource (hikari/make-datasource datasource-schema)]
      (timbre/info "Started DataSource component")
      (assoc this :datasource        datasource
                  :datasource-schema datasource-schema)))

  (stop [this]
    (timbre/info "Stopping DataSource component")
    (let [datasource (:datasource this)]
      (hikari/close-datasource datasource)
      (timbre/info "Stopped DataSource component")
      (dissoc this :datasource :datasource-schema))))

(defn new-datasource
  "Returns instance of DataSource component."
  []
  (map->DataSource {}))
