(ns wedding.api.controller.audit
  (:refer-clojure :exclude [get])
  (:require [ring.util.http-response :refer [ok]]
            [wedding.service.audit :as service]))

(defn get
  [{:keys [ctx parameters]}]
  (let [query-params (-> parameters :query)
        tables (service/get! (:datasource ctx) query-params)]
    (ok tables)))
