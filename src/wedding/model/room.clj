(ns wedding.model.room
  (:require [wedding.lib.db.utils :as query]))

(def table-name :accommodation_rooms)
(def view-table-name :v_accommodation_rooms)
(def view-table-name-available :v_accommodation_rooms_available)

(defn create!
  "Creates new room."
  [connection room]
  (query/insert! connection table-name room))

(defn update!
  "Updates a room."
  [connection id room]
  (query/update-by-id! connection table-name id room))

(defn get-by-id!
  "Returns room by given id."
  [connection id]
  (query/get-by-id! connection view-table-name id))

(defn get-all!
  "Returns all rooms."
  ([connection]
   (get-all! connection {}))
  ([connection filters]
   (query/get-all! connection view-table-name filters)))

(defn delete!
  "Deletes room by id."
  [connection id]
  (query/delete! connection table-name {:id id}))

(defn get-all-available!
  "Returns all available rooms."
  ([connection]
   (get-all-available! connection {}))
  ([connection filters]
   (query/get-all! connection view-table-name-available filters)))
