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

(defn beer-drinkers
  "Returns guests who will drink beer at the wedding."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (guest-model/get-all! connection {:is_beer_drinker true})))

(defn wine-drinkers
  "Returns guests who will drink wine at the wedding."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (guest-model/get-all! connection {:is_wine_drinker true})))

(defn guests-with-room
  "Returns guests who have room assigned."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (guest-model/get-guests-with-room! connection)))

(defn guests-without-room
  "Returns guests who are waiting for a room."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (guest-model/get-guests-without-room! connection)))

(defn bundled-statistics
  "Returns all statistics for RSVP and accommodation bundled together."
  [datasource]
  (let [rsvp-answered          (rsvp-answered datasource)
        rsvp-unanswered        (rsvp-unanswered datasource)
        accommodation-accepted (accommodation-accepted datasource)
        accommodation-declined (accommodation-declined datasource)
        beer-drinkers          (beer-drinkers datasource)
        wine-drinkers          (wine-drinkers datasource)
        guests-with-room       (guests-with-room datasource)
        guests-without-room    (guests-without-room datasource)]
    {:rsvp_answered_count          (:count rsvp-answered)
     :rsvp_unanswered_count        (:count rsvp-unanswered)
     :accommodation_accepted_count (:count accommodation-accepted)
     :accommodation_declined_count (:count accommodation-declined)
     :beer_drinkers_count          (:count beer-drinkers)
     :wine_drinkers_count          (:count wine-drinkers)
     :guests_with_room_count       (:count guests-with-room)
     :guests_without_room_count    (:count guests-without-room)}))
