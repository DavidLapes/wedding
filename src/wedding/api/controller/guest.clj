(ns wedding.api.controller.guest
  (:refer-clojure :exclude [update get])
  (:require [ring.util.http-response :refer [created ok]]
            [wedding.lib.api.http-response :refer [response-message]]
            [wedding.service.guest :as service]))

(defn rsvp
  "Creates RSVP record with a new guest."
  [{:keys [ctx parameters body-params]}]
  (let [id (-> parameters :path :id)
        email-notification-adapter (-> ctx :notification-adapter :email)
        result (service/rsvp! (:datasource ctx) email-notification-adapter id body-params)]
    (if (= result :success)
      (response-message "RSVP creation has been successful")
      (response-message "RSVP creation has failed"))))

(defn create
  "Creates new guest."
  [{:keys [ctx body-params]}]
  (let [guest (service/create! (:datasource ctx) body-params)]
    (created "/api/private/guests" guest)))

(defn update
  "Updates guest."
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
        guest (service/get-by-id! (:datasource ctx) id)]
    (ok guest)))

(defn delete
  "Deletes guest by id."
  [{:keys [ctx parameters]}]
  (let [id (-> parameters :path :id)]
    (service/delete! (:datasource ctx) id)
    (ok (response-message "Successfully deleted"))))
