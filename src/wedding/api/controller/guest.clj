(ns wedding.api.controller.guest
  (:refer-clojure :exclude [update get])
  (:require [ring.util.http-response :refer [created ok]]
            [wedding.service.guest :as service]))

(defn rsvp
  "Creates RSVP record with a new guest."
  [{:keys [ctx body-params]}]
  (service/rsvp! (:datasource ctx) body-params))

(defn create
  "Creates new guest."
  [{:keys [ctx body-params]}]
  (let [guest (service/create! (:datasource ctx) body-params)]
    (created "/api/private" guest)))

(defn update
  "Updates new guest."
  [{:keys [ctx parameters body-params]}]
  (let [id    (-> parameters :path :id)
        guest (service/update! (:datasource ctx) id body-params)]
    (ok guest)))

(defn get
  "Returns list of guests."
  [{:keys [ctx]}]
  (let [guests (service/get! (:datasource ctx))]
    (ok guests)))

(defn get-by-id
  "Returns guest by id."
  [{:keys [ctx parameters]}]
  (let [id     (-> parameters :path :id)
        guests (service/get-by-id! (:datasource ctx) id)]
    (ok guests)))
