(ns wedding.api.controller.table
  (:refer-clojure :exclude [update get])
  (:require [ring.util.http-response :refer [created ok]]
            [wedding.service.table :as service]))

(defn create
  "Creates new table."
  [{:keys [ctx body-params]}]
  (let [table (service/create! (:datasource ctx) body-params)]
    (created "/api/private/tables" table)))

(defn update
  "Updates table."
  [{:keys [ctx parameters body-params]}]
  (let [id    (-> parameters :path :id)
        table (service/update! (:datasource ctx) id body-params)]
    (ok table)))

(defn get
  "Returns list of guests."
  [{:keys [ctx]}]
  (let [tables (service/get! (:datasource ctx))]
    (ok tables)))

(defn get-by-id
  "Returns table by id."
  [{:keys [ctx parameters]}]
  (let [id     (-> parameters :path :id)
        table (service/get-by-id! (:datasource ctx) id)]
    (ok table)))
