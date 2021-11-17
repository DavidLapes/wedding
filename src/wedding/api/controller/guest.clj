(ns wedding.api.controller.guest
  (:refer-clojure :exclude [update get])
  (:require [ring.util.http-response :refer [created ok internal-server-error]]
            [wedding.lib.api.http-response :refer [add-response-message response-message]]
            [wedding.service.guest :as service]))

(defn get-rsvp-guests
  "Returns list of guests who have not responded to RSVP form yet."
  [{:keys [ctx]}]
  (let [datasource (:datasource ctx)
        guests (service/get-rsvp-guests! datasource)]
    (ok guests)))

(defn rsvp
  "Creates RSVP record with a new guest."
  [{:keys [ctx parameters body-params]}]
  (let [id (-> parameters :path :id)
        audit-logger (:audit-logger ctx)
        email-notification-adapter (-> ctx :notification-adapter :email)
        result (service/rsvp! (:datasource ctx) audit-logger email-notification-adapter id body-params)]
    (println result)
    (if (= result :success)
      (response-message "RSVP creation has been successful")
      (add-response-message (internal-server-error) "RSVP creation has failed"))))

(defn create
  "Creates new guest."
  [{:keys [ctx body-params]}]
  (let [guest (service/create! (:datasource ctx) body-params)]
    (created "/api/private/guests" guest)))

(defn update
  "Updates guest."
  [{:keys [ctx parameters body-params]}]
  (let [id    (-> parameters :path :id)
        audit-logger (:audit-logger ctx)
        guest (service/update! (:datasource ctx) audit-logger id body-params)]
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
  (let [id (-> parameters :path :id)
        audit-logger (:audit-logger ctx)]
    (service/delete! (:datasource ctx) audit-logger id)
    (ok (response-message "Successfully deleted"))))
