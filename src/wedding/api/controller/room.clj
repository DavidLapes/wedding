(ns wedding.api.controller.room
  (:refer-clojure :exclude [update get])
  (:require [ring.util.http-response :refer [created ok]]
            [wedding.service.room :as service]))

(defn create
  "Creates new room."
  [{:keys [ctx body-params]}]
  (let [room (service/create! (:datasource ctx) body-params)]
    (created "/api/private/rooms" room)))

(defn update
  "Updates room."
  [{:keys [ctx parameters body-params]}]
  (let [id    (-> parameters :path :id)
        room (service/update! (:datasource ctx) id body-params)]
    (ok room)))

(defn get
  "Returns list of rooms."
  [{:keys [ctx]}]
  (let [rooms (service/get! (:datasource ctx))]
    (ok rooms)))

(defn get-all-available
  "Returns list of available rooms."
  [{:keys [ctx]}]
  (let [rooms (service/get-all-available! (:datasource ctx))]
    (ok rooms)))

(defn get-by-id
  "Returns room by id."
  [{:keys [ctx parameters]}]
  (let [id     (-> parameters :path :id)
        room (service/get-by-id! (:datasource ctx) id)]
    (ok room)))

(defn delete
  "Deletes room by id."
  [{:keys [ctx parameters]}]
  (let [id (-> parameters :path :id)]
    (service/delete! (:datasource ctx) id)
    (ok)))
