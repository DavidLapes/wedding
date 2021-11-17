(ns wedding.service.statistics
  (:require [wedding.model.guest :as guest-model]
            [clojure.java.jdbc :as jdbc]))

(defn rsvp-answered
  "Returns guests who have already answered RSVP."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (guest-model/get-all! connection {:rsvp_answered true})))

(defn rsvp-unanswered [datasource]
  "Returns guests who have not yet answered RSVP."
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (guest-model/get-all! connection {:rsvp_answered false})))

(defn accommodation-accepted
  "Returns guests who want the accommodation at the wedding."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (guest-model/get-all! connection {:accommodation true
                                      :rsvp_answered true})))

(defn accommodation-declined
  "Returns guests who don't want the accommodation at the wedding."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (guest-model/get-all! connection {:accommodation false
                                      :rsvp_answered true})))

(defn bundled-statistics
  "Returns all statistics for RSVP and accommodation bundled together."
  [datasource]
  (let [rsvp-answered          (rsvp-answered datasource)
        rsvp-unanswered        (rsvp-unanswered datasource)
        accommodation-accepted (accommodation-accepted datasource)
        accommodation-declined (accommodation-declined datasource)]
    {:rsvp_answered_count          (:count rsvp-answered)
     :rsvp_unanswered_count        (:count rsvp-unanswered)
     :accommodation_accepted_count (:count accommodation-accepted)
     :accommodation_declined_count (:count accommodation-declined)}))
