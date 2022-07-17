(ns wedding.api.controller.statistics
  (:require [ring.util.http-response :refer [ok]]
            [wedding.service.statistics :as service]))

(defn rsvp-answered
  "Returns guests who have already answered RSVP."
  [{:keys [ctx]}]
  (let [datasource (:datasource ctx)
        data (service/rsvp-answered datasource)]
    (ok data)))

(defn rsvp-unanswered
  "Returns guests who have not yet answered RSVP."
  [{:keys [ctx]}]
  (let [datasource (:datasource ctx)
        data (service/rsvp-unanswered datasource)]
    (ok data)))

(defn accommodation-declined
  "Returns guests who don't want the accommodation at the wedding."
  [{:keys [ctx]}]
  (let [datasource (:datasource ctx)
        data (service/accommodation-declined datasource)]
    (ok data)))

(defn accommodation-accepted
  "Returns guests who want the accommodation at the wedding."
  [{:keys [ctx]}]
  (let [datasource (:datasource ctx)
        data (service/accommodation-accepted datasource)]
    (ok data)))

(defn beer-drinkers
  "Returns guests who will drink beer at the wedding."
  [{:keys [ctx]}]
  (let [datasource (:datasource ctx)
        data (service/beer-drinkers datasource)]
    (ok data)))

(defn wine-drinkers
  "Returns guests who will drink wine at the wedding."
  [{:keys [ctx]}]
  (let [datasource (:datasource ctx)
        data (service/wine-drinkers datasource)]
    (ok data)))

(defn guests-with-room
  "Returns guests who have room assigned."
  [{:keys [ctx]}]
  (let [datasource (:datasource ctx)
        data (service/guests-with-room datasource)]
    (ok data)))

(defn guests-without-room
  "Returns guests who are waiting for a room."
  [{:keys [ctx]}]
  (let [datasource (:datasource ctx)
        data (service/guests-without-room datasource)]
    (ok data)))

(defn bundled-statistics
  "Returns all statistics for RSVP and accommodation bundled together."
  [{:keys [ctx]}]
  (let [datasource (:datasource ctx)
        data (service/bundled-statistics datasource)]
    (ok data)))
