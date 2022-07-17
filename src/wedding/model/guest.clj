(ns wedding.model.guest
  (:require [wedding.lib.db.utils :as query]))

(def table-name :guests)
(def view-table-name :v_guests)
(def view-rsvp-guests :v_rsvp_guests)
(def view-guests-with-room :v_guests_with-room)
(def view-guests-without-room :v_guests_without_room)

(defn create!
  "Creates new guest."
  [connection guest]
  (query/insert! connection table-name guest))

(defn update!
  "Updates a guest."
  [connection id guest]
  (query/update-by-id! connection table-name id guest))

(defn get-by-id!
  "Returns guest by given id."
  [connection id]
  (query/get-by-id! connection view-table-name id))

(defn get-all!
  "Returns all guests."
  ([connection]
   (get-all! connection {}))
  ([connection filters]
   (query/get-all! connection view-table-name filters)))

(defn get-rsvp-guests!
  "Returns all guests who have not responded to RSVP form yet."
  [connection]
  (query/get-all! connection view-rsvp-guests {}))

(defn get-guests-with-room!
  "Returns guests who have room assigned."
  [connection]
  (query/get-all! connection view-guests-with-room {}))

(defn get-guests-without-room!
  "Returns guests who are waiting for a room."
  [connection]
  (query/get-all! connection view-guests-without-room {}))

(defn delete!
  "Deletes guest by id."
  [connection id]
  (query/delete! connection table-name {:id id}))
